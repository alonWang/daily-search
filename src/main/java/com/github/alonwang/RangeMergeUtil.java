package com.github.alonwang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class RangeMergeUtil {

    /**
     * 在一组有序不交叉不重复的数字边界中,插入一个新的数字
     * 1. 如果列表为空,新增边界
     * 2. 如果已存在,无变化
     * 3. 如果相连(左相连,右相连,左右均相连),融合
     * 4. 如果不相连,新增一个边界
     * 详见测试用例
     *
     * @param list 不为null
     * @param num  插入数字
     * @return 插入成功 true, 插入失败 false
     */
    public static boolean insert(List<Bound> list, int num) {
        final Bound newBound = new Bound(num, num);
        if (list.isEmpty()) {
            list.add(newBound);
            return true;
        }

        //num之前的Bound下标
        int previous = -1;
        //num之后的Bound下标
        int next = -1;
        for (int i = 0; i < list.size(); i++) {
            //已包含无需再处理
            int compare = list.get(i).compareTo(num);
            if (compare == 0) {
                return false;
            }

            if (compare == -1) {
                previous = i;
            } else if (next == -1) {
                next = i;
            }
        }

        boolean mergeSuccess = false;
        if (previous != -1 && next != -1) {
            mergeSuccess = mergeBound(list, num, previous, next);
        } else if (previous != -1) {
            mergeSuccess = mergePrevious(num, list, previous);
        } else {
            mergeSuccess = mergeNext(num, list, next);
        }
        //两侧都未发生merge,插入新Bound
        if (!mergeSuccess) {
            list.add(previous + 1, newBound);
        }
        return true;
    }

    private static boolean mergeBound(List<Bound> list, int i, int previous, int next) {
        boolean mergePrevious = mergePrevious(i, list, previous);
        boolean mergeNext = mergeNext(i, list, next);
        //两边都merge成功了,合并previous和next
        if (mergePrevious & mergeNext) {
            Bound previousBound = list.get(previous);
            Bound nextBound = list.get(next);
            list.add(previous + 1, new Bound(previousBound.getStart(), nextBound.getEnd()));
            list.remove(previousBound);
            list.remove(nextBound);
        }
        return mergePrevious | mergeNext;
    }

    private static boolean mergeNext(int i, List<Bound> list, int next) {
        Bound nextBound = list.get(next);
        if (nextBound.getStart() == i + 1) {
            nextBound.setStart(i);
            list.set(next, nextBound);
            return true;
        }
        return false;
    }

    private static boolean mergePrevious(int i, List<Bound> list, int previous) {
        Bound previousBound = list.get(previous);
        if (previousBound.getEnd() == i - 1) {
            previousBound.setEnd(i);
            list.set(previous, previousBound);
            return true;
        }
        return false;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Bound {
        private int start;
        private int end;

        public static Bound of(int start, int end) {
            return new Bound(start, end);
        }

        public int compareTo(int i) {
            if (start > i) {
                return 1;
            } else if (end < i) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
