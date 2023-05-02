package city.olooe.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class CGVParser {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.parse(new URL("http://www.cgv.co.kr/movies/detail-view/?midx=85541"), 600);
//        Document doc = Jsoup.parse(new URL("http://www.cgv.co.kr/movies/detail-view/?midx=86720"), 600);
        String info = doc.selectFirst(".sect-story-movie").text();
        String engtitle = doc.selectFirst(".sect-base-movie .spec dt").text();

        Element ele = doc.selectFirst("#ct100_PlaceHolderContent_Section_Still_Cut");
        Elements els = doc.select(".sect-base-movie .spec dt");

        for (Element e : els) {
            Elements es = e.getElementsContainingText("감독").next().select("a");
            for (Element element : es ) {
                String director = element.text();
                String directorHref = element.attr("href");
                String pidx = directorHref.substring(directorHref.lastIndexOf("=") + 1);
                System.out.println(director);
            }
            Elements es2 = e.getElementsContainingText("배우").next().select("a");
            for (Element element : es2) {
                String actor = element.text();
                String actorHref = element.attr("href");
                String pidx = actorHref.substring(actorHref.lastIndexOf("=") + 1);
                System.out.println(actor);
            }
            Elements es3 = e.getElementsContainingText("장르");
            for (Element element : es3) {
                String genre = element.text();
                System.out.println(genre);
            }
            Elements es4 = e.getElementsContainingText("기본").next();
            for (Element element : es4) {
                String runningTime = element.text().split(",")[1].trim();
                String nation = element.text().split(",")[2].trim();
                System.out.println(nation);
            }
//            String str = e.text();
//            if (str.trim().length() != 0) {
//                System.out.println(str);
//            }
        }
//        System.out.println(doc);

//        URL url = new URL("http://www.cgv.co.kr/movies/");
//        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
//        String str = "";
//        while ((str = br.readLine()) != null) {
//            System.out.println(br.readLine());
//        }

//        Element ele = doc.selectFirst(".sect-movie-chart");

//        Elements lis = ele.select("li");
//        for (Element e : lis) {
//            System.out.println(e.selectFirst("a").attr("href"));

//            String title = e.selectFirst(".box-contents strong.title").text();
//            String href = e.selectFirst("a").attr("href");
//            String date = e.selectFirst(".txt-info").text().replaceAll("개봉", "").trim();
//            String imgSrc = e.selectFirst(".thumb-image img").attr("src");
//            String imgAlt = e.selectFirst(".thumb-image img").attr("alt");
//            String age = e.selectFirst("i.cgvIcon").text();
//
//            System.out.println(title);
//            System.out.println(href);
//            System.out.println(date);
//            System.out.println(imgSrc);
//            System.out.println(imgAlt);
//            System.out.println(age);

            // 상세페이지
//            doc = Jsoup.parse(new URL("http://www.cgv.co.kr" + e.selectFirst("a").attr("href")), 2000);
//            Element el = doc.selectFirst(".sect-base-movie");
//            String title = doc.selectFirst(".sect-base-movie h3 strong").text();
//
//            System.out.println(el);
//            System.out.println(title);
//            break;
//        }

//        Elements els = doc.select(".sect-movie-chart");
//        System.out.println(els.size());
//
//        for (int i = 0; i <els.size(); i++) {
//            Element e = els.get(i);
//            System.out.println(e);
//        }
//
//        for (int i = 0; i < 50; i++) {
//            System.out.println(doc.selectXpath("//*[@id=\"contents\"]/div[1]/div[3]/ol[1]/li[" + i +"]/div[1]/a"));
//        }
    }
}
