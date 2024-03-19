package store.mybooks.resource.elastic.repository;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import store.mybooks.resource.book.dto.response.BookBriefResponse;
import store.mybooks.resource.elastic.entity.Elastic;

/**
 * packageName    : store.mybooks.resource.elastic.repository <br/>
 * fileName       : ElasticRepositoryImpl<br/>
 * author         : newjaehun <br/>
 * date           : 3/19/24<br/>
 * description    :<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE<br/>
 * -----------------------------------------------------------<br/>
 * 3/19/24        newjaehun       최초 생성<br/>
 */
@RequiredArgsConstructor
public class ElasticRepositoryImpl implements ElasticRepositoryCustom {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Page<BookBriefResponse> search(String query, Pageable pageable) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query != null ? QueryBuilders.multiMatchQuery(query,
                        "book_name", "publisher_name", "author_names", "tag_names", "book_explanation")
                        : QueryBuilders.matchAllQuery())
                .withPageable(pageable)
                .build();

        SearchHits<Elastic> searchHits = elasticsearchOperations.search(searchQuery, Elastic.class);
        List<BookBriefResponse> responses = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(elastic -> new BookBriefResponse(
                        elastic.getBookId(),
                        elastic.getImage(),
                        elastic.getName(),
                        elastic.getRate(),
                        elastic.getCost(),
                        elastic.getSaleCost()
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, searchHits.getTotalHits());
    }
}
