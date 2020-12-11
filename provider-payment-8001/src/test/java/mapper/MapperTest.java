package mapper;

import com.alibaba.fastjson.JSON;
import com.atguigu.springcloud.domin.bo.User;
import com.atguigu.springcloud.service.PaymentService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hexingquan
 * @Date: 2020/11/1 5:15 下午
 */
public class MapperTest extends BaseTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Test
    public void test1() throws IOException {
        System.out.println(paymentService.createIndex());

    }

    @Test
    public void testExit() throws IOException {
        GetIndexRequest request = new GetIndexRequest("hxq");
        System.out.println(restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT));
    }

    @Test
    public void testExit1() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("dangdang");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    @Test
    public void testExit2() throws IOException {
        User user = new User("核心圈", 23);
        IndexRequest request = new IndexRequest("hxq");
        request.id("123");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.type("_doc");
        request.source(JSON.toJSON(user), XContentType.JSON);

        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(index.toString());
        System.out.println(index.status());

    }

    @Test
    public void testExit3() throws IOException {
        GetRequest getRequest = new GetRequest("hxq", "_doc", "123");
        getRequest.fetchSourceContext(new FetchSourceContext(false)).storedFields("_none_");
        System.out.println(restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT));

    }

    @Test
    public void testExit4() throws IOException {
        GetRequest getRequest = new GetRequest("hxq", "_doc", "123");
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
        System.out.println(response);

    }

    @Test
    public void testExit5() throws IOException {
        UpdateRequest request = new UpdateRequest("hxq", "_doc", "123");
        request.timeout("1s");
        User user = new User("wudangshan", 23);
        request.doc(JSON.toJSONString(user), XContentType.JSON);
        UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(update.status());

    }

    @Test
    public void testExit6() throws IOException {
        DeleteRequest request = new DeleteRequest("hxq", "_doc", "123");
        request.timeout("1s");
        System.out.println(restHighLevelClient.delete(request, RequestOptions.DEFAULT).status());

    }

    @Test
    public void testExit7() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        List<User> userList = new ArrayList<>();
        userList.add(new User("张三", 1));
        userList.add(new User("请见", 3));
        userList.add(new User("十三水·", 1));
        userList.add(new User("测试·", 23));
        userList.add(new User("看看·", 1));

        for (int i = 0; i < userList.size(); i++) {
            bulkRequest.add(new IndexRequest("hxq", "_doc").source(JSON.toJSONString(userList.get(i)), XContentType.JSON));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures());

    }

    @Test
    public void testExit8() throws IOException {
        SearchRequest searchRequest = new SearchRequest("hxq");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // HighlightBuilder highlighter = sourceBuilder.highlighter();
        sourceBuilder.from(0);
        sourceBuilder.sort("sdf",SortOrder.ASC);
        sourceBuilder.size(10);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "张");
        sourceBuilder.query(termQueryBuilder).timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(search.getHits()));
        System.out.println("==========================");
        for (SearchHit hit : search.getHits().getHits()) {

            System.out.println(hit.getSourceAsMap());
        }
    }
    @Test
    public void testExit9() throws IOException {
        SearchRequest searchRequest = new SearchRequest("hxq");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "张");
        sourceBuilder.query(termQueryBuilder).timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(search.getHits()));
        System.out.println("==========================");
        for (SearchHit hit : search.getHits().getHits()) {
            // 解析高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if(title!=null){
                Text[] fragments = title.fragments();
                StringBuilder nTitle= new StringBuilder();
                for (Text fragment : fragments) {
                    nTitle.append(fragment);
                }
                sourceAsMap.put("title", nTitle.toString());
            }

            System.out.println(hit.getSourceAsMap());
        }
    }

}
