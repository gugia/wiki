package com.personal.wiki.duplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * wiki
 *
 * @author L.X <gugia@qq.com>
 */
public class AbstractClassExample {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new MoneyTask(100));
        service.execute(new MoneyTask(1));
        service.execute(new MoneyTask(50));
        service.shutdown();
    }
}

abstract class AbstractPerson {
    protected Money money;

    class Money {
        private int count;

        public Money(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}

class Andy extends AbstractPerson {

    Andy(int cash) {
        this.money = new Money(cash);
    }

    public int show() {
        return this.money.getCount();
    }
}

class Tom extends AbstractPerson {

    Tom(int cash) {
        this.money = new Money(cash);
    }

    public int show() {
        return this.money.getCount();
    }
}

class MoneyTask implements Runnable {

    private final int money;

    MoneyTask(int money) {
        this.money = money;
    }

    @Override
    public void run() {
        Andy andy = new Andy(money);
        Tom tom = new Tom(money + 100);
        System.out.println("tom has cash:" + tom.show());
        System.out.println("andy has cash:" + andy.show());
    }
}
