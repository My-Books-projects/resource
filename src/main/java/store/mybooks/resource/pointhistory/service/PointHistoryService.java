package store.mybooks.resource.pointhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.mybooks.resource.bookorder.entity.BookOrder;
import store.mybooks.resource.bookorder.exception.BookOrderNotExistException;
import store.mybooks.resource.bookorder.repository.BookOrderRepository;
import store.mybooks.resource.pointhistory.dto.mapper.PointHistoryMapper;
import store.mybooks.resource.pointhistory.dto.request.PointHistoryCreateRequest;
import store.mybooks.resource.pointhistory.dto.response.PointHistoryCreateResponse;
import store.mybooks.resource.pointhistory.dto.response.PointHistoryResponse;
import store.mybooks.resource.pointhistory.dto.response.PointResponse;
import store.mybooks.resource.pointhistory.entity.PointHistory;
import store.mybooks.resource.pointhistory.repository.PointHistoryRepository;
import store.mybooks.resource.pointrule.entity.PointRule;
import store.mybooks.resource.pointrule.exception.PointRuleNotExistException;
import store.mybooks.resource.pointrule.repository.PointRuleRepository;
import store.mybooks.resource.pointrulename.entity.PointRuleName;
import store.mybooks.resource.pointrulename.repository.PointRuleNameRepository;
import store.mybooks.resource.user.entity.User;
import store.mybooks.resource.user.exception.UserNotExistException;
import store.mybooks.resource.user.repository.UserRepository;

/**
 * packageName    : store.mybooks.resource.point_history.service<br>
 * fileName       : PointHistoryService<br>
 * author         : minsu11<br>
 * date           : 3/7/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/7/24        minsu11       최초 생성<br>
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PointHistoryService {
    private final UserRepository userRepository;
    private final BookOrderRepository bookOrderRepository;
    private final PointRuleRepository pointRuleRepository;
    private final PointRuleNameRepository pointRuleNameRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final PointHistoryMapper pointHistoryMapper;

    /**
     * methodName : getRemainingPoint<br>
     * author : minsu11<br>
     * description : 유저의 잔여 포인트 조회.
     * <br> *
     *
     * @param userId 잔여 포인트 조회 할 회원 {@code id}
     * @return point response
     */
    @Transactional(readOnly = true)
    public PointResponse getRemainingPoint(Long userId) {
        return pointHistoryRepository.getRemainingPoint(userId);
    }

    /**
     * methodName : getPointHistory<br>
     * author : minsu11<br>
     * description : 포인트 내역.
     * <br> *
     *
     * @param pageable 페이징
     * @param userId
     * @return page
     */
    @Transactional(readOnly = true)
    public Page<PointHistoryResponse> getPointHistory(Pageable pageable, Long userId) {
        return pointHistoryRepository.getPointHistoryByUserId(pageable, userId);
    }

    /**
     * methodName : createPointHistory<br>
     * author : minsu11<br>
     * description : 포인트 내역 생성.
     * <br>
     *
     * @param request the request
     * @param userId  the user id
     * @return the point history create response
     */
    public PointHistoryCreateResponse createPointHistory(PointHistoryCreateRequest request, Long userId) {
        System.out.println(request.getPointName());
        PointRuleName pointRulename = pointRuleNameRepository.findById(request.getPointName())
                .orElseThrow(PointRuleNotExistException::new);

        PointRule pointRule = pointRuleRepository.findPointRuleByPointRuleName(pointRulename.getId())
                .orElseThrow(PointRuleNotExistException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistException(userId));

        BookOrder bookOrder = bookOrderRepository.findByNumber(request.getOrderNumber())
                .orElseThrow(BookOrderNotExistException::new);

        PointHistory pointHistory = new PointHistory(request.getPointCost(), user, pointRule, bookOrder);
        return pointHistoryMapper.mapToPointHistoryCreateResponse(pointHistoryRepository.save(pointHistory));
    }

}
