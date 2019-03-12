package com.itheima.Field;

import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.entity.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引流程实现
 */
public class App1_index_create {
    /**
     * 需求：创建索引库，生成索引文件。
     * 在哪里存储索引文件：E:\library\index
     */
    private final static String PATH = "E:\\library\\index";

    public static void main(String[] args) throws Exception {
        //1.采集数据
        List<Book> bookList = new BookDaoImpl().findAllBooks();

        //2.把从数据库采集的数据，转换为Document文档对象
        List<Document> documentList = new ArrayList<>();
        for (Book b : bookList){
            //2.1 创建Document文档对象
            Document document = new Document();
            /**
             * 2.2 给文档添加域
             * TextField 文本域
             *    参数1：域的名称，可以随意定义，只要唯一。一般和表的列一致。
             *    参数2：域的值
             *    参数3：是否存储域的信息。
             */
            document.add(new StringField("id",b.getId()+"", Field.Store.YES));
            document.add(new TextField("bookname",b.getBookname()+"", Field.Store.YES));
            document.add(new FloatField("price",b.getPrice(), Field.Store.YES));
            document.add(new StoredField("pic",b.getPic()));
            document.add(new TextField("bookdesc",b.getBookdesc(), Field.Store.NO));

            //2.3 把文档对象添加到集合
            documentList.add(document);
        }
        //3. 创建分析器对象，用于分词（切词）
        Analyzer analyzer = new StandardAnalyzer();
        //4. 创建索引库配置对象，配置索引库
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);//为了测试方便
        //5. 创建索引库目录对象，指定索引库目录地址
        Directory dictionary = FSDirectory.open(new File(PATH));
        //6. 创建索引库操作对象，用于把文档写入索引文件。
        IndexWriter indexWriter = new IndexWriter(dictionary,conf);
        for (Document document:documentList){
            //7. 把文档写入索引库
            indexWriter.addDocument(document);
        }
        //8. 关闭释放资源
        indexWriter.commit();
        indexWriter.close();
    }
}
