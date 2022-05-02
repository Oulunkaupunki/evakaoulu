// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.emailclient.config

import fi.espoo.evaka.emailclient.IEmailMessageProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("evakaoulu")
@Configuration
class EmailConfiguration {

    @Bean
    fun emailMessageProvider(): IEmailMessageProvider = EmailMessageProvider()
}

internal class EmailMessageProvider(): IEmailMessageProvider {
    override val subjectForPendingDecisionEmail: String = "Toimenpiteitäsi odotetaan"
    override val subjectForClubApplicationReceivedEmail: String = "Hakemus vastaanotettu"
    override val subjectForDaycareApplicationReceivedEmail: String = "Hakemus vastaanotettu"
    override val subjectForPreschoolApplicationReceivedEmail: String = "Hakemus vastaanotettu"


    override fun getPendingDecisionEmailHtml(): String {
        return """
            <p>Olet saanut päätöksen/ilmoituksen Oulun varhaiskasvatukselta, joka odottaa toimenpiteitäsi. Myönnetty varhaiskasvatus-/kerhopaikka tulee hyväksyä tai hylätä kahden viikon sisällä päätöksen saapumisesta.</p>
            
            <p>Hakemuksen tekijä voi hyväksyä tai hylätä varhaiskasvatus-/kerhopaikan kirjautumalla osoitteeseen <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi</a> tai ottamalla yhteyttä päätöksellä mainittuun päiväkodin johtajaan.</p>
            
            <p>Tähän viestiin ei voi vastata.</p>
        """.trimIndent()
    }

    override fun getPendingDecisionEmailText(): String {
        return """
            Olet saanut päätöksen/ilmoituksen Oulun varhaiskasvatukselta, joka odottaa toimenpiteitäsi. Myönnetty varhaiskasvatus-/kerhopaikka tulee hyväksyä tai hylätä kahden viikon sisällä päätöksen saapumisesta.
            
            Hakemuksen tekijä voi hyväksyä tai hylätä varhaiskasvatus-/kerhopaikan kirjautumalla osoitteeseen varhaiskasvatus.ouka.fi tai ottamalla yhteyttä päätöksellä mainittuun päiväkodin johtajaan.            
            
            Tähän viestiin ei voi vastata.
        """.trimIndent()
    }

    override fun getClubApplicationReceivedEmailHtml(): String {
        return """
            <p>Hei!</p>
            
            <p>Olemme vastaanottaneet lapsenne hakemuksen avoimeen varhaiskasvatukseen. Pyydämme teitä olemaan yhteydessä suoraan toivomanne päiväkodin johtajaan ja tiedustelemaan vapaata avoimen varhaiskasvatuksen kerhopaikkaa.</p>
            
            <p>Hakemuksia käsitellään pääsääntöisesti vastaanottopäivämäärän mukaan. Sisarukset valitaan myös hakujärjestyksessä, ellei ole erityisperustetta.</p>
            
            <p>Päätös on nähtävissä ja hyväksyttävissä/hylättävissä <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi.</a></p>

            <p>
            Ystävällisesti <br/>
            Varhaiskasvatuksen palveluohjaus <br/>
            </p>
            
            <p>Tähän viestiin ei voi vastata.</p>
        """.trimIndent()
    }

    override fun getClubApplicationReceivedEmailText(): String {
        return """
            Hei! 
            
            Olemme vastaanottaneet lapsenne hakemuksen avoimeen varhaiskasvatukseen. Pyydämme teitä olemaan yhteydessä suoraan toivomanne päiväkodin johtajaan ja tiedustelemaan vapaata avoimen varhaiskasvatuksen kerhopaikkaa. 
            
            Hakemuksia käsitellään pääsääntöisesti vastaanottopäivämäärän mukaan. Sisarukset valitaan myös hakujärjestyksessä, ellei ole erityisperustetta. 
           
            Päätös on nähtävissä ja hyväksyttävissä/hylättävissä varhaiskasvatus.ouka.fi.  
            
            Ystävällisesti,  
            
            Varhaiskasvatuksen palveluohjaus 
            
            Tähän viestiin ei voi vastata.
        """.trimIndent()
    }

    override fun getDaycareApplicationReceivedEmailHtml(): String {
        return """
            <p>Hei!</p>
            
            <p>Lapsenne varhaiskasvatushakemus on vastaanotettu. Hakemuksen tehnyt huoltaja voi muokata hakemusta osoitteessa <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi</a> siihen saakka, kunnes palveluohjaus ottaa sen käsittelyyn. Varhaiskasvatuspaikan järjestelyaika on neljä kuukautta. Mikäli kyseessä on vanhemman äkillinen työllistyminen tai opintojen alkaminen, järjestelyaika on kaksi viikkoa. Toimittakaa tällöin työ- tai opiskelutodistus hakemuksen liitteeksi. Kahden viikon järjestelyaika alkaa todistuksen saapumispäivämäärästä. Jatketun aukiolon ja vuorohoidon palveluita järjestetään vanhempien vuorotyön perusteella.</p>
            
            <p><b>Mikäli lapsellenne järjestyy varhaiskasvatuspaikka jostakin hakemuksessa toivomastanne kunnallisesta varhaiskasvatuspaikasta</b>, ilmoitamme teille paikan viimeistään kaksi viikkoa ennen varhaiskasvatuksen toivottua aloitusajankohtaa. Muussa tapauksessa olemme teihin yhteydessä.</p>
            
            <p><b>Mikäli valitsitte ensimmäiseksi hakutoiveeksi yksityisen päiväkodin tai perhepäivähoitajan</b>, olkaa suoraan yhteydessä kyseiseen palveluntuottajaan varmistaaksenne varhaiskasvatuspaikan saamisen. Mikäli toivomanne palveluntuottaja ei pysty tarjoamaan varhaiskasvatuspaikkaa, pyydämme teitä olemaan yhteydessä varhaiskasvatuksen palveluohjaukseen.</p>
            
            <p><b>Siirtohakemukset</b> (lapsella on jo varhaiskasvatuspaikka Oulun kaupungin varhaiskasvatusyksikössä) käsitellään pääsääntöisesti hakemuksen saapumispäivämäärän mukaan. Merkittäviä syitä siirtoon ovat: aikaisemman varhaiskasvatuspaikan lakkauttaminen, sisarukset ovat eri yksiköissä, pitkä matka, huonot kulkuyhteydet, lapsen ikä, ryhmän ikärakenne, vuorohoidon tarpeen loppuminen sekä huomioon otettavat erityisperusteet.</p>
            
            <p><b>Mikäli ilmoititte hakemuksessa lapsenne tuen tarpeesta</b>, varhaiskasvatuksen erityisopettaja on teihin yhteydessä, jotta lapsen tuen tarpeet voidaan ottaa huomioon paikkaa osoitettaessa.</p>
            
            <p>Päätös on nähtävissä ja hyväksyttävissä/hylättävissä <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi.</a></p>
            
            <p>Hakemuksen liitteet voi lisätä suoraan sähköiselle hakemukselle <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi</a> tai postitse osoitteeseen Varhaiskasvatuksen palveluohjaus, PL 75, 90015 Oulun kaupunki.</p>

            <p>
            Ystävällisesti <br/>
            Varhaiskasvatuksen palveluohjaus <br/>
            </p>
            
            <p>Tähän viestiin ei voi vastata.</p>
            
        """.trimIndent()
    }

    override fun getDaycareApplicationReceivedEmailText(): String {
        return """
            Hei! 

            Lapsenne varhaiskasvatushakemus on vastaanotettu. Hakemuksen tehnyt huoltaja voi muokata hakemusta osoitteessa varhaiskasvatus.ouka.fi siihen saakka, kunnes palveluohjaus ottaa sen käsittelyyn. Varhaiskasvatuspaikan järjestelyaika on neljä kuukautta. Mikäli kyseessä on vanhemman äkillinen työllistyminen tai opintojen alkaminen, järjestelyaika on kaksi viikkoa. Toimittakaa tällöin työ- tai opiskelutodistus hakemuksen liitteeksi. Kahden viikon järjestelyaika alkaa todistuksen saapumispäivämäärästä. Jatketun aukiolon ja vuorohoidon palveluita järjestetään vanhempien vuorotyön perusteella. 

            Mikäli lapsellenne järjestyy varhaiskasvatuspaikka jostakin hakemuksessa toivomastanne kunnallisesta varhaiskasvatuspaikasta, ilmoitamme teille paikan viimeistään kaksi viikkoa ennen varhaiskasvatuksen toivottua aloitusajankohtaa. Muussa tapauksessa olemme teihin yhteydessä.  

            Mikäli valitsitte ensimmäiseksi hakutoiveeksi yksityisen päiväkodin tai perhepäivähoitajan, olkaa suoraan yhteydessä kyseiseen palveluntuottajaan varmistaaksenne varhaiskasvatuspaikan saamisen. Mikäli toivomanne palveluntuottaja ei pysty tarjoamaan varhaiskasvatuspaikkaa, pyydämme teitä olemaan yhteydessä varhaiskasvatuksen palveluohjaukseen. 

            Siirtohakemukset (lapsella on jo varhaiskasvatuspaikka Oulun kaupungin varhaiskasvatusyksikössä) käsitellään pääsääntöisesti hakemuksen saapumispäivämäärän mukaan. Merkittäviä syitä siirtoon ovat: aikaisemman varhaiskasvatuspaikan lakkauttaminen, sisarukset ovat eri yksiköissä, pitkä matka, huonot kulkuyhteydet, lapsen ikä, ryhmän ikärakenne, vuorohoidon tarpeen loppuminen sekä huomioon otettavat erityisperusteet. 

            Mikäli ilmoititte hakemuksessa lapsenne tuen tarpeesta, varhaiskasvatuksen erityisopettaja on teihin yhteydessä, jotta lapsen tuen tarpeet voidaan ottaa huomioon paikkaa osoitettaessa.  

            Päätös on nähtävissä ja hyväksyttävissä/hylättävissä varhaiskasvatus.ouka.fi.  

            Hakemuksen liitteet voi lisätä suoraan sähköiselle hakemukselle varhaiskasvatus.ouka.fi tai postitse osoitteeseen Varhaiskasvatuksen palveluohjaus, PL 75, 90015 Oulun kaupunki. 
            
            Ystävällisesti, 
            Varhaiskasvatuksen palveluohjaus 
                        
            Tähän viestiin ei voi vastata.
        """.trimIndent()
    }

    override fun getPreschoolApplicationReceivedEmailHtml(withinApplicationPeriod: Boolean): String {
        return """
            <p>Hei!</p>
            
            <p>Olemme vastaanottaneet lapsenne ilmoittautumisen esiopetukseen. Hakemuksen tehnyt huoltaja voi muokata hakemusta siihen saakka, kunnes palveluohjaus ottaa sen käsittelyyn. Varhaiskasvatuksen palveluohjaus sijoittaa kaikki esiopetukseen ilmoitetut lapset esiopetusyksiköihin maaliskuun aikana. Päätös on nähtävissä ja hyväksyttävissä/hylättävissä <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi.</a></p>
            
            <p>Mikäli hakemaanne yksikköön ei perusteta esiopetusryhmää, palveluohjaus on teihin yhteydessä ja tarjoaa paikkaa sellaisesta yksiköstä, johon esiopetusryhmä on muodostunut.</p>
            
            <p>Mikäli ilmoititte hakemuksessa lapsenne tuen tarpeesta, varhaiskasvatuksen erityisopettaja on teihin yhteydessä, jotta lapsen tuen tarpeet voidaan ottaa huomioon paikkaa osoitettaessa.</p>
            
            <p><b>ESIOPETUKSEEN LIITTYVÄ VARHAISKASVATUS</b></p>
            
            <p>Mikäli hait esiopetukseen liittyvää varhaiskasvatusta, otathan huomioon:</p>
            <ul><li>Varhaiskasvatuspaikan järjestelyaika on neljä kuukautta. Jatketun aukiolon ja vuorohoidon palveluita järjestetään vanhempien vuorotyön tai iltaisin ja/tai viikonloppuisin tapahtuvan opiskelun perusteella.</li>
            <li><b>Mikäli lapsellenne järjestyy varhaiskasvatuspaikka jostakin hakemuksessa toivomastanne kunnallisesta varhaiskasvatuspaikasta,</b> ilmoitamme teille paikan viimeistään kaksi viikkoa ennen varhaiskasvatuksen toivottua aloitusajankohtaa. Muussa tapauksessa olemme teihin yhteydessä.</li>
            <li><b>Mikäli valitsitte ensimmäiseksi hakutoiveeksi yksityisen päiväkodin</b>, olkaa suoraan yhteydessä kyseiseen yksikköön varmistaaksenne varhaiskasvatuspaikan saamisen. Mikäli toivomanne palveluntuottaja ei pysty tarjoamaan varhaiskasvatuspaikkaa, pyydämme teitä olemaan yhteydessä varhaiskasvatuksen palveluohjaukseen.</li>
            <li><b>Siirtohakemukset</b> (lapsella on jo varhaiskasvatuspaikka Oulun kaupungin varhaiskasvatusyksikössä) käsitellään pääsääntöisesti hakemuksen saapumispäivämäärän mukaan. Merkittäviä syitä siirtoon ovat: aikaisemman varhaiskasvatuspaikan lakkauttaminen, sisarukset ovat eri yksiköissä, pitkä matka, huonot kulkuyhteydet, lapsen ikä, ryhmän ikärakenne, vuorohoidon tarpeen loppuminen sekä huomioon otettavat erityisperusteet.</li>
            </ul>
            <p>Päätös on nähtävissä ja hyväksyttävissä/hylättävissä <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi.</a></p>
            
            <p>Hakemuksen liitteet lisätään suoraan sähköiselle hakemukselle eVakassa.</p>
            
            <p>
            Ystävällisesti <br/>
            Varhaiskasvatuksen palveluohjaus <br/>
            </p>
            
            <p>Tähän viestiin ei voi vastata.</p>
            
        """.trimIndent()
    }

    override fun getPreschoolApplicationReceivedEmailText(withinApplicationPeriod: Boolean): String {
        return """
            Hei! 

            Olemme vastaanottaneet lapsenne ilmoittautumisen esiopetukseen. Hakemuksen tehnyt huoltaja voi muokata hakemusta siihen saakka, kunnes palveluohjaus ottaa sen käsittelyyn. Varhaiskasvatuksen palveluohjaus sijoittaa kaikki esiopetukseen ilmoitetut lapset esiopetusyksiköihin maaliskuun aikana. Päätös on nähtävissä ja hyväksyttävissä/hylättävissä varhaiskasvatus.ouka.fi.  

            Mikäli hakemaanne yksikköön ei perusteta esiopetusryhmää, palveluohjaus on teihin yhteydessä ja tarjoaa paikkaa sellaisesta yksiköstä, johon esiopetusryhmä on muodostunut.          

            Mikäli ilmoititte hakemuksessa lapsenne tuen tarpeesta, varhaiskasvatuksen erityisopettaja on teihin yhteydessä, jotta lapsen tuen tarpeet voidaan ottaa huomioon paikkaa osoitettaessa.  

            ESIOPETUKSEEN LIITTYVÄ VARHAISKASVATUS 

            Mikäli hait esiopetukseen liittyvää varhaiskasvatusta, otathan huomioon: 

            - Varhaiskasvatuspaikan järjestelyaika on neljä kuukautta. Jatketun aukiolon ja vuorohoidon palveluita järjestetään vanhempien vuorotyön tai iltaisin ja/tai viikonloppuisin tapahtuvan opiskelun perusteella. 

            - Mikäli lapsellenne järjestyy varhaiskasvatuspaikka jostakin hakemuksessa toivomastanne kunnallisesta varhaiskasvatuspaikasta, ilmoitamme teille paikan viimeistään kaksi viikkoa ennen varhaiskasvatuksen toivottua aloitusajankohtaa. Muussa tapauksessa olemme teihin yhteydessä.  

            - Mikäli valitsitte ensimmäiseksi hakutoiveeksi yksityisen päiväkodin, olkaa suoraan yhteydessä kyseiseen yksikköön varmistaaksenne varhaiskasvatuspaikan saamisen. Mikäli toivomanne palveluntuottaja ei pysty tarjoamaan varhaiskasvatuspaikkaa, pyydämme teitä olemaan yhteydessä varhaiskasvatuksen palveluohjaukseen. 

            - Siirtohakemukset (lapsella on jo varhaiskasvatuspaikka Oulun kaupungin varhaiskasvatusyksikössä) käsitellään pääsääntöisesti hakemuksen saapumispäivämäärän mukaan. Merkittäviä syitä siirtoon ovat: aikaisemman varhaiskasvatuspaikan lakkauttaminen, sisarukset ovat eri yksiköissä, pitkä matka, huonot kulkuyhteydet, lapsen ikä, ryhmän ikärakenne, vuorohoidon tarpeen loppuminen sekä huomioon otettavat erityisperusteet. 

            Päätös on nähtävissä ja hyväksyttävissä/hylättävissä varhaiskasvatus.ouka.fi.  

            Hakemuksen liitteet lisätään suoraan sähköiselle hakemukselle eVakassa. 

            Ystävällisesti, 
            Varhaiskasvatuksen palveluohjaus 
                        
            Tähän viestiin ei voi vastata.
        """.trimIndent()
    }

}