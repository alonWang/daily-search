import com.github.alonwang.MergeRange
import spock.lang.Specification

import static com.github.alonwang.MergeRange.Bound.of

class MergeRangeTest extends Specification {

    def "test bound"() {

        when:

        def ranges = MergeRange.insert(array, i)
        then:
        ranges[idx] == bound
        ranges.size() == size

        where:
        array                | i  | idx | bound      | size
        //为空
        []                   | 3  | 0   | of(3, 3)   | 1
        //存在merge,边界变化
        [of(1, 2), of(7, 9)] | 3  | 0   | of(1, 3)   | 2
        [of(1, 2), of(7, 9)] | 6  | 1   | of(6, 9)   | 2
        [of(1, 2), of(7, 9)] | 10 | 1   | of(7, 10)  | 2
        //无merge  新增bound
        [of(3, 4), of(6, 9)] | 1  | 0   | of(1, 1)   | 3
        [of(3, 4), of(6, 9)] | 11 | 2   | of(11, 11) | 3
        //左右merge,bound减少
        [of(3, 4), of(6, 7)] | 5  | 0   | of(3, 7)   | 1


    }
}
