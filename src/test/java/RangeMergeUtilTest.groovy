import com.github.alonwang.RangeMergeUtil
import spock.lang.Specification

import static com.github.alonwang.RangeMergeUtil.Bound.of

class RangeMergeUtilTest extends Specification {

    def "test bound"() {

        when:

        def result = RangeMergeUtil.insert(array, i)
        then:
        array[idx] == bound
        array.size() == size
        result == success

        where:
        array                | i  | idx | bound      | size | success
        //为空
        []                   | 3  | 0   | of(3, 3)   | 1    | true
        //已存在
        [of(1, 2), of(7, 9)] | 2  | 0   | of(1, 2)   | 2    | false
        //存在merge,边界变化
        [of(1, 2), of(7, 9)] | 3  | 0   | of(1, 3)   | 2    | true
        [of(1, 2), of(7, 9)] | 6  | 1   | of(6, 9)   | 2    | true
        [of(1, 2), of(7, 9)] | 10 | 1   | of(7, 10)  | 2    | true
        //无merge  新增bound
        [of(3, 4), of(6, 9)] | 1  | 0   | of(1, 1)   | 3    | true
        [of(3, 4), of(6, 9)] | 11 | 2   | of(11, 11) | 3    | true
        //左右merge,bound减少
        [of(3, 4), of(6, 7)] | 5  | 0   | of(3, 7)   | 1    | true


    }
}
