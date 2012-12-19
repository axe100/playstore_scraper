package riesware;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import riesware.entities.App;
import riesware.entities.Permission;
import riesware.repositories.AppRepository;
import riesware.repositories.PermissionRepository;

public class Application {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
    private AppRepository apps;
    private PermissionRepository permissions;
    private static final String config = "spring-config.xml";
    private static ApplicationContext context;
    private static Application main;

    private static void load() {
        context = new ClassPathXmlApplicationContext(config);
        main = context.getBean(Application.class);
        log.info("Application loaded successfully");
    }
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IOException {
        load();
        

        int range = 0;

        for (int i = 0; i < 1; i++) {

            StringBuilder sb = new StringBuilder();

            String rangeString = String.valueOf(range);

            sb.append("https://play.google.com/store/apps/category/APPLICATION/collection/topselling_paid?start=").append(rangeString).append("&num=24");

            String currentAppPageLink = sb.toString();

            Document doc = Jsoup.connect(currentAppPageLink).get();

            Elements links = doc.select("a[class=title]");

            

            for (int j = 0; j < links.size(); j++) {

                String currentLink = links.get(j).attr("href");

                logger.info("ADD LinkToApp: " + j + " " + currentLink);

                StringBuilder applinksb = new StringBuilder();
                
                applinksb.append("https://play.google.com").append(currentLink);

                Document appdoc = Jsoup.connect(applinksb.toString()).get();

                Elements appName = appdoc.getElementsByClass("doc-banner-title");
                Element appDescription = appdoc.getElementById("doc-original-text");
                Element appPrice = appdoc.select("span[itemprop=price]").get(0);
                Element appDownloads = appdoc.select("dd[itemprop=numDownloads]").get(0);
                Element appCategory = appdoc.select("a[href*=/store/apps/category/]").get(0);
                Element appRating = appdoc.select("div[itemprop=ratingValue]").get(0);
                Element appRatingCount = appdoc.select("span[itemprop=ratingCount]").get(0);

                String nameString = appName.get(0).text();
                String descriptionString = appDescription.text();
                String priceString = appPrice.attr("content");
                String downloadsString = appDownloads.ownText();
                String categoryString = appCategory.text();
                String ratingString = appRating.attr("content");
                String ratingCountString = appRatingCount.text();

                logger.info("APP NAME: " + nameString);
                logger.info("APP DES: " + descriptionString);
                logger.info("APP PRICE: " + priceString);
                logger.info("APP DOWNLOADS: " + downloadsString);
                logger.info("APP CATEGORY: " + categoryString);
                logger.info("APP RATING: " + ratingString);
                logger.info("APP RATING COUNT: " + ratingCountString);


                //PERMISSIONS

                Elements myPermissions = appdoc.getElementsByClass("doc-permission-description");

                for (int k = 0; k < myPermissions.size(); k++) {

                    String currentPermission = myPermissions.get(k).text();

                    logger.info("PERMISSION: " + i + " " + currentPermission);

                }

            }

            range = range + 24;

        }
        
        
        Permission p1 = main.getPermission("some test permission");        
        Permission p2 = main.getPermission("another test permission");
        
        App app = new App();
        app.addPermission(p1);
        app.addPermission(p2);
        
        app.setAppId("some.id"); // app id must be set
        app.setName("Sample App");
        app.setDescription("Sample Description");
        
        
        //Er beschwert sich wegen static und not static, wie kann ich das lösen.
        apps.save(app);
   
    }

    public Permission getPermission(String permission) {
        
        Permission p = permissions.findByPermissionString(permission);

        if (p == null) {
            p = new Permission();
            p.setPermissionString(permission);
            permissions.save(p);
        }

        return p;
    }
    
    public App getApp(String appString) {
        
        App a = apps.findOne(appString);
        
        if(a == null) {
        
            a = new App();
        }
        return a;
    }
    
}
