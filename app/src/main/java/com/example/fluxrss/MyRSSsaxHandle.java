package tp.android.fluxrss;
import android.util.Log;

import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MyRSSsaxHandler extends DefaultHandler {

    private String url;
    private boolean inItem = false;
    private boolean inTitle = false;
    private boolean inDescription = false;
    private boolean inDate = false;

    private StringBuilder title;
    private StringBuilder description;
    private StringBuilder date;
    private String imageURL;

    private final List<tp.android.fluxrss.Article> articles = new ArrayList<>();

    public void setUrl(String url) {
        this.url = url;
    }

    public List<tp.android.fluxrss.Article> getArticles() {
        return articles;
    }

    public void processFeed() {

        try {

            articles.clear();

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            InputStream inputStream = new URL(url).openStream();

            parser.parse(inputStream, this);

            inputStream.close();

        } catch (Exception e) {
            Log.e("RSS", "Erreur pendant le parsing", e);
        }
    }

    @Override
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes) throws SAXException {

        String name = qName;

        if (name.equals("item")) {

            inItem = true;

            title = new StringBuilder();
            description = new StringBuilder();
            date = new StringBuilder();

            imageURL = null;

        } else if (inItem && name.equals("title")) {

            inTitle = true;

        } else if (inItem && name.equals("description")) {

            inDescription = true;

        } else if (inItem && name.equals("pubDate")) {

            inDate = true;

        } else if (inItem && name.equals("media:content")) {

            imageURL = attributes.getValue("url");

        } else if (inItem && name.equals("enclosure")) {

            imageURL = attributes.getValue("url");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        String chars = new String(ch, start, length);

        if (inTitle) {
            title.append(chars);
        }

        if (inDescription) {
            description.append(chars);
        }

        if (inDate) {
            date.append(chars);
        }
    }

    @Override
    public void endElement(
            String uri,
            String localName,
            String qName) throws SAXException {

        String name = qName;

        if (name.equals("title")) {

            inTitle = false;

        } else if (name.equals("description")) {

            inDescription = false;

        } else if (name.equals("pubDate")) {

            inDate = false;

        } else if (name.equals("item")) {

            inItem = false;

            Article article = new Article(
                    title.toString().trim(),
                    date.toString().trim(),
                    description.toString().trim(),
                    imageURL
            );

            articles.add(article);

            Log.d("RSS", "Titre : " + article.getTitle());
            Log.d("RSS", "Date : " + article.getDate());
            Log.d("RSS", "Description : " + article.getDescription());
            Log.d("RSS", "Image : " + article.getImageUrl());
        }
    }
}