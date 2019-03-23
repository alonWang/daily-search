package com.github.alonwang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MergeRange {

    /**
     * 在一组有序不交叉不重复的数字边界中,插入一个新的数字
     * 1. 如果已存在,返回null
     * 2. 如果相连,融合
     * 3. 如果不相连,新增一个边界
     * 详见测试用例
     *
     * @param list 不为null
     * @param num 插入数字
     * @return 如果已存在 返回null ,其他情况均返回更新后的边界组
     */
    public static List<Bound> insert(List<Bound> list, int num) {
        final Bound newBound = new Bound(num, num);
        if (list.isEmpty()) {
            list.add(newBound);
            return list;
        }

        //小于i的最大idx
        int floor = -1;
        //大于i的最小idx
        int ceil = -1;
        for (int i = 0; i < list.size(); i++) {
            //已包含无需再处理
            int compare = list.get(i).compareTo(num);
            if (compare == 0) {
                return null;
            } else if (compare == -1) {
                floor = i;
            } else if (ceil == -1) {
                ceil = i;
            }
        }

        boolean mergeSuccess = false;
        if (floor != -1 && ceil != -1) {
            mergeSuccess = mergeBound(list, num, floor, ceil);
        } else if (floor != -1) {
            mergeSuccess = mergeFloor(num, list, floor);
        } else {
            mergeSuccess = mergeCeil(num, list, ceil);
        }
        //两侧都未发生merge,新增bound
        if (!mergeSuccess) {
            list.add(floor + 1, newBound);
        }
        return list;
    }

    private static boolean mergeBound(List<Bound> list, int i, int floor, int ceil) {
        boolean mergeFloor = mergeFloor(i, list, floor);
        boolean mergeCeil = mergeCeil(i, list, ceil);
        //两边都merge成功了,合并floor和ceil
        if (mergeFloor & mergeCeil) {
            Bound floorBound = list.get(floor);
            Bound ceilBound = list.get(ceil);
            list.add(floor + 1, new Bound(floorBound.getStart(), ceilBound.getEnd()));
            list.remove(floorBound);
            list.remove(ceilBound);
        }
        return mergeFloor | mergeCeil;
    }

    private static boolean mergeCeil(int i, List<Bound> list, int ceil) {
        Bound ceilBound = list.get(ceil);
        if (ceilBound.getStart() == i + 1) {
            ceilBound.setStart(i);
            list.set(ceil, ceilBound);
            return true;
        }
        return false;
    }

    private static boolean mergeFloor(int i, List<Bound> list, int floor) {
        Bound floorBound = list.get(floor);
        if (floorBound.getEnd() == i - 1) {
            floorBound.setEnd(i);
            list.set(floor, floorBound);
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
