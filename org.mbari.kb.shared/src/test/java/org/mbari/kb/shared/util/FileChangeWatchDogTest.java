package org.mbari.kb.shared.util;

/**
 * Created by brian on 3/4/14.
 */
public class FileChangeWatchDogTest {

    // @Rule
    // public TemporaryFolder folder = new TemporaryFolder();

    // @Test
    // public void testCreate() throws IOException, InterruptedException {
    // long timeout = 3000L;
    // final File file = folder.newFile(getClass().getSimpleName() +
    // "-testCreate.txt");
    // assertTrue("WHOOPS, " + file.getAbsolutePath() + " is not empty.",
    // file.length() == 0);
    // Function<File, Long> fn = new Function<File, Long>() {
    // @Override
    // public Long apply(File file) {
    // return file.length();
    // }
    // };
    // System.out.println("Starting");
    // FileChangeWatchDog<Long> watchDog = new FileChangeWatchDog<Long>(file,
    // timeout, fn, ENTRY_MODIFY);
    // ListenableFuture<Long> future = watchDog.getFuture();
    // Futures.addCallback(future, new FutureCallback<Long>() {
    // @Override
    // public void onSuccess(Long aLong) {
    // System.out.println(file.getAbsolutePath() + " is " + aLong + " bytes");
    // assertTrue(aLong > 0);
    // }

    // @Override
    // public void onFailure(Throwable throwable) {
    // fail(throwable.getMessage());
    // }
    // });
    // watchDog.start();
    // BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    // writer.write("Hello, World!\n");
    // writer.close();
    // Thread.sleep(timeout);

    // }

}
