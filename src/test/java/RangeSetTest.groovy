import com.google.common.collect.Range
import com.google.common.collect.TreeRangeSet
import spock.lang.Specification

/**
 *
 * @author: weilong.wang* @create: 2019/3/25
 *
 */
class RangeSetTest extends Specification {
    def "test range set"() {
        when:
        def rangeSet = TreeRangeSet.<Integer> create()
        rangeSet.addAll(ranges)
        then:
        rangeSet.asRanges().size() == size
        where:
        ranges                                                                      | size
        [Range.closedOpen(1, 3), Range.closedOpen(7, 10), Range.closedOpen(1, 2)]   | 2
        [Range.closedOpen(1, 3), Range.closedOpen(7, 10), Range.closedOpen(3, 4)]   | 2
        [Range.closedOpen(1, 3), Range.closedOpen(7, 10), Range.closedOpen(6, 7)]   | 2
        [Range.closedOpen(1, 3), Range.closedOpen(7, 10), Range.closedOpen(10, 11)] | 2
        [Range.closedOpen(1, 3), Range.closedOpen(7, 10), Range.closedOpen(3, 7)]   | 1
        [Range.closedOpen(1, 3), Range.closedOpen(7, 10), Range.closedOpen(4, 6)]   | 3

    }
}