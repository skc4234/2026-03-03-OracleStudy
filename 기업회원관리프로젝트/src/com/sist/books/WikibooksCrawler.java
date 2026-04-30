package com.sist.books;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/*
 * <ul id="book-list" class="list">
                        
<li class="row unstyled book-list-item" target="https://wikibook.co.kr/ai-agents-dev">
    <div class="col-md-1 book-list-image">
        <a href="https://wikibook.co.kr/ai-agents-dev/">
            <img src="https://wikibook.co.kr/images/cover/s/9791158396756.jpg">
        </a>
    </div>
    <div class="col-md-11 book-list-detail">
        <a class="book-url" href="https://wikibook.co.kr/ai-agents-dev"><h4 class="main-title">AI 에이전트 개발 완벽 입문</h4></a>
            <div class="sub-title">에이전트 기초부터 RAG, ReAct, 펑션 콜링, 랭그래프, CrewAI, smolagents, A2A, n8n, MCP, 클로드 코드, 오픈클로까지 실전 에이전트 개발을 위한 모든 것</div>
            <div class="book-info">
            <span class="author">에디 유, 김대규, 조경아, 김현지 <small>지음</small></span> |
                        42,000원 |
            <span class="pub-date">2026년 4월 28일 | </span>
            <span class="isbn"><small>ISBN: </small>9791158396756</span>
            <span class="tag" style="display:none">MCP,클로드 코드,오픈클로,CrewAI,n8n,에이전트,RAG,랭그래프</span>
        </div>
                <span id="tags">
            <i class='bx bxs-purchase-tag-alt' ></i>
                    <a href="https://wikibook.co.kr/tag/mcp/"><span class="label label-default">MCP</span></a>
                    <a href="https://wikibook.co.kr/tag/%ed%81%b4%eb%a1%9c%eb%93%9c-%ec%bd%94%eb%93%9c/"><span class="label label-default">클로드 코드</span></a>
                    <a href="https://wikibook.co.kr/tag/%ec%98%a4%ed%94%88%ed%81%b4%eb%a1%9c/"><span class="label label-default">오픈클로</span></a>
                    <a href="https://wikibook.co.kr/tag/crewai/"><span class="label label-default">CrewAI</span></a>
                    <a href="https://wikibook.co.kr/tag/n8n/"><span class="label label-default">n8n</span></a>
                    <a href="https://wikibook.co.kr/tag/%ec%97%90%ec%9d%b4%ec%a0%84%ed%8a%b8/"><span class="label label-default">에이전트</span></a>
                    <a href="https://wikibook.co.kr/tag/rag/"><span class="label label-default">RAG</span></a>
                    <a href="https://wikibook.co.kr/tag/%eb%9e%ad%ea%b7%b8%eb%9e%98%ed%94%84/"><span class="label label-default">랭그래프</span></a>
                </span>
            </div>
</li>

 * 
 */
public class WikibooksCrawler {
   
   public static void main(String[] args) {
	  try
	  {
		  BooksDAO dao = BooksDAO.newInstance();
		  for(int i=1;i<=65;i++)
		  {
			 try
			 {
			  Document doc=
					 Jsoup.connect("https://wikibook.co.kr/list/page/"+i).get();
			  System.out.println("현재 페이지:"+i);
			  Elements poster=doc.select("ul#book-list li.book-list-item div.book-list-image a img");
			  Elements title=doc.select("ul#book-list li.book-list-item div.book-list-detail h4.main-title");
			  Elements subject=doc.select("ul#book-list li.book-list-item div.book-list-detail div.sub-title");
			  Elements author=doc.select("ul#book-list li.book-list-item div.book-list-detail span.author");
			  Elements pubdate=doc.select("ul#book-list li.book-list-item div.book-list-detail span.pub-date");
			  Elements isbn=doc.select("ul#book-list li.book-list-item div.book-list-detail span.isbn");
			  Elements tag=doc.select("ul#book-list li.book-list-item div.book-list-detail span.tag");
			  
			  for(int j=0;j<poster.size();j++)
			  {
				  System.out.println("이미지:"+poster.get(j).attr("src"));
				  System.out.println("제목:"+title.get(j).text());
				  System.out.println("내용:"+subject.get(j).text());
				  System.out.println("저자:"+author.get(j).text());
				  System.out.println("출판일:"+pubdate.get(j).text());
				  System.out.println("ISBN:"+isbn.get(j).text());
				  System.out.println("태그:"+tag.get(j).text());
				  String price=doc.select("div.book-info").text().split("\\|")[1].trim();
				  if(price.contains("옮김"))
				  {
					  price="30,000원";
				  }
				  System.out.println("가격:"+price);
				  System.out.println("===================================");
				  BooksVO vo = new BooksVO();
				  vo.setBookname(title.get(j).text());
				  vo.setContent(subject.get(j).text());
				  vo.setAuthor(author.get(j).text());
				  vo.setPubdate(pubdate.get(j).text());
				  vo.setIsbn(isbn.get(j).text());
				  vo.setTag(tag.get(j).text());
				  vo.setPoster(poster.get(j).attr("src"));
				  vo.setPrice(price);
				  dao.insertBooks(vo);
				  
			  }
			 }catch(Exception ex) {}
		  }
		  System.out.println("@@@@@@@@@@@@데이터 수집 완료@@@@@@@@@@@@");
	  }catch(Exception ex){}
   }
}
