package com.sutton.bgJobs;

import com.sutton.dao.BookmarkDao;
import com.sutton.entities.Weblink;
import com.sutton.util.HttpConnect;
import com.sutton.util.IOUtil;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class WebpageDownloaderTask implements Runnable {

    private static BookmarkDao dao = new BookmarkDao();
    private  static final long TIME_FRAME  = 3000000000L; //3 secs
    private boolean downloadAll = false;
    ExecutorService downloadExecutor = Executors.newFixedThreadPool(5);

    private static class Downloader<T extends Weblink> implements Callable<T>{
        private T weblink;

        public Downloader(T weblink) {
            this.weblink = weblink;
        }

        @Override
        public T call()  {

            try {
                if(!weblink.getUrl().endsWith(".pdf")) {
                    weblink.setDownloadStatus(Weblink.DownloadStatus.FAILED);
                    String htmlPage = HttpConnect.download(weblink.getUrl());
                    weblink.setHtmlPage(htmlPage);
                } else {
                    weblink.setDownloadStatus(Weblink.DownloadStatus.NOT_ELIGIBLE);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return weblink;
        }
    }

    public  WebpageDownloaderTask(boolean downloadAll){
        this.downloadAll = downloadAll;
    }


    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            //Get weblinks
            List<Weblink> weblinks = getWeblinks();


            // Download concurrently
            if(weblinks.size() > 0) {
                download(weblinks);
            } else {
                System.out.println("No new weblink to download");
            }

            // Wait
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        downloadExecutor.shutdown();
    }

    private void download(List<Weblink> weblinks) {
        List<Future<Weblink>> futures = new ArrayList<>();
        List<Downloader<Weblink>> tasks = getTasks(weblinks);

        try {
            futures = downloadExecutor.invokeAll(tasks,TIME_FRAME,TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        futures.forEach(future -> {
            if(!future.isCancelled()) {
                try {
                    Weblink weblink = future.get();
                    String webPage = weblink.getHtmlPage();
                    if(webPage != null){
                        IOUtil.write(webPage,weblink.getId());
                        weblink.setDownloadStatus(Weblink.DownloadStatus.SUCCESS);
                        System.out.println("Download success : " + weblink.getUrl());
                    } else {
                        System.out.println("Webpage not downloaded : " + weblink.getUrl() );
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private List<Downloader<Weblink>> getTasks(List<Weblink> weblinks) {
        List<Downloader<Weblink>> tasks = new ArrayList<>();
        weblinks.forEach(weblink -> tasks.add(new Downloader<>(weblink)));
        return tasks;
    }

    private List<Weblink> getWeblinks() {
        List<Weblink> weblinks;

        if(downloadAll){
            weblinks = dao.getAllWebLinks();
            downloadAll = false;
        } else {
            weblinks = dao.getWebLinks(Weblink.DownloadStatus.NOT_ATTEMPTED);
        }
        return weblinks;
    }
}
