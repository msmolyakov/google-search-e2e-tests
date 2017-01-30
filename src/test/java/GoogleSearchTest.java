import org.junit.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class GoogleSearchTest {

    @Test
    public void selenideSearchGivesRelevantSiteAndImageOnTheTop() {
        open("http://google.com/ncr");
        $("input[name='q']").val("selenide").pressEnter();
        String firstResultLink = $("div#ires h3 a").attr("href");
        assertThat(firstResultLink, containsString("selenide.org"));

        $("div#top_nav a[href*='tbm=isch']").click();
        String imageMetaInfo = executeJavaScript(
                "return document.getElementById('rg').getElementsByClassName('rg_meta')[0].innerHTML");
        assertThat(imageMetaInfo, containsString("selenide.org"));

        $("div#top_nav a.qs:not( [href*='tbm='] )").click();
        $("div#ires h3 a").shouldHave(attribute("href", firstResultLink));
    }

}
