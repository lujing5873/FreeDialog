package com.example.freedialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class RxTimerUtil {

    private static Disposable mDisposable;
    private static Disposable mOneDisposable;
    /** milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public static void timer(long milliseconds,final IRxNext next) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .debounce(500,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mOneDisposable=disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if(next!=null){
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //取消订阅
                        cancelOne();
                    }

                    @Override
                    public void onComplete() {
                        //取消订阅
                        cancelOne();
                    }
                });
    }


    /** 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public static void interval(long milliseconds,final IRxNext next){
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable=disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if(next!=null){
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 取消订阅
     */
    public static void cancelOne(){
        if(mOneDisposable!=null&&!mOneDisposable.isDisposed()){
            mOneDisposable.dispose();

        }
        mOneDisposable=null;
    }


    /**
     * 取消订阅
     */
    public static void cancel(){
        cancelOne();
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();

        }
        mDisposable=null;
    }

    public  interface IRxNext{
        void doNext(long number);
    }
}