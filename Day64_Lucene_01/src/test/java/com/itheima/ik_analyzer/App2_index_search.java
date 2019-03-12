package com.itheima.ik_analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class App2_index_search {
    /**
     * 需求：根据bookname进行搜索，搜索图书名称中包含java的图书信息。
     * 索引文件路径：E:\library\index
     */
    private final static String PATH="E:\\library\\index";
    /**
     1.    建立分析器对象（Analyzer），用于分词
     2.    建立查询对象（Query）
     3.    建立索引库目录对象（Directory），指定索引库的位置
     4.    建立索引读取对象（IndexReader），把索引数据读取内存中
     5.    建立索引搜索对象（IndexSearcher），执行搜索，返回搜索的结果集（TopDocs）
     6.    处理结果集
     7.    释放资源
     */
    public static void main(String[] args) throws Exception {
//        Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        QueryParser queryParser = new QueryParser("bookname",analyzer);
        Query query = queryParser.parse("bookname:java");
        Directory directory = FSDirectory.open(new File(PATH));
        IndexReader indexReader = DirectoryReader.open(directory);
        // 关键对象： 创建索引库搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        TopDocs topDocs = indexSearcher.search(query, 10);
        System.out.println("搜索总结果："+topDocs.totalHits);
        // 获取搜索结果
        // ScoreDoc 包含了文档的id、分值
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc:scoreDocs){
            System.out.println("==========================");
            // 根据ScoreDoc提供的score属性，获取分值。
            System.out.println("文档分值:"+scoreDoc.score);
            // 根据ScoreDoc提供的文档id，获取文档对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            // 获取文档域
            System.out.println("图书ID:"+document.get("id"));
            System.out.println("图书名称:"+document.get("bookname"));
            System.out.println("图书价格:"+document.get("price"));
            System.out.println("图书路径:"+document.get("pic"));
            System.out.println("图书描述:"+document.get("bookdesc"));
        }
        //关闭reader
        indexReader.close();
    }
}
