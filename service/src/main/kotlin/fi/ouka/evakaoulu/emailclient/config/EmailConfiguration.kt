// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.emailclient.config

import fi.espoo.evaka.EvakaEnv
import fi.espoo.evaka.daycare.domain.Language
import fi.espoo.evaka.emailclient.CalendarEventNotificationData
import fi.espoo.evaka.emailclient.EmailContent
import fi.espoo.evaka.emailclient.IEmailMessageProvider
import fi.espoo.evaka.invoicing.service.IncomeNotificationType
import fi.espoo.evaka.messaging.MessageThreadStub
import fi.espoo.evaka.messaging.MessageType
import fi.espoo.evaka.shared.ChildId
import fi.espoo.evaka.shared.MessageThreadId
import fi.espoo.evaka.shared.domain.FiniteDateRange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@Profile("evakaoulu")
@Configuration
class EmailConfiguration {

    @Bean
    fun emailMessageProvider(env: EvakaEnv): IEmailMessageProvider = EmailMessageProvider(env)
}

internal class EmailMessageProvider(private val env: EvakaEnv) : IEmailMessageProvider {
    val subjectForPendingDecisionEmail: String = "Toimenpiteitäsi odotetaan / Waiting for your action"
    val subjectForClubApplicationReceivedEmail: String = "Hakemus vastaanotettu / Application received"
    val subjectForDaycareApplicationReceivedEmail: String = "Hakemus vastaanotettu / Application received"
    val subjectForPreschoolApplicationReceivedEmail: String = "Hakemus vastaanotettu / Application received"
    val subjectForDecisionEmail: String = "Päätös eVakassa / Decision in eVaka"
    private fun link(language: Language, path: String): String {
        val baseUrl =
            when (language) {
                Language.sv -> env.frontendBaseUrlSv
                else -> env.frontendBaseUrlFi
            }
        val url = "$baseUrl$path"
        return """<a href="$url">$url</a>"""
    }

    private fun calendarLink(language: Language) = link(language, "/calendar")
    private fun messageLink(language: Language, threadId: MessageThreadId) =
        link(language, "/messages/$threadId")
    private fun childLink(language: Language, childId: ChildId) =
        link(language, "/children/$childId")
    private fun incomeLink(language: Language) = link(language, "/income")

    private fun unsubscribeLink(language: Language) =
        link(language, "/personal-details#notifications")

    private val unsubscribeFi =
        """<p><small>Jos et halua enää saada tämänkaltaisia viestejä, voit muuttaa asetuksia eVakan Omat tiedot -sivulla: ${unsubscribeLink(Language.fi)}</small></p>"""
    private val unsubscribeSv =
        """<p><small>Om du inte längre vill ta emot meddelanden som detta, kan du ändra dina inställningar på eVakas Personuppgifter-sida: ${unsubscribeLink(Language.sv)}</small></p>"""
    private val unsubscribeEn =
        """<p><small>If you no longer want to receive messages like this, you can change your settings on eVaka's Personal information page: ${unsubscribeLink(Language.en)}</small></p>"""
    override fun calendarEventNotification(
        language: Language,
        events: List<CalendarEventNotificationData>
    ): EmailContent {
        val format =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale("fi", "FI"))
        val eventsHtml =
            "<ul>" +
                events.joinToString("\n") { event ->
                    var period = event.period.start.format(format)
                    if (event.period.end != event.period.start) {
                        period += "-${event.period.end.format(format)}"
                    }
                    "<li>$period: ${event.title}</li>"
                } +
                "</ul>"
        return EmailContent.fromHtml(
            subject =
            "Uusia kalenteritapahtumia eVakassa / New calendar events in eVaka",
            html =
            """
        <p>eVakaan on lisätty uusia kalenteritapahtumia:</p>
        $eventsHtml
        <p>Katso lisää kalenterissa: ${calendarLink(Language.fi)}</p>
        $unsubscribeFi
        <hr>
        <p>New calendar events in eVaka:</p>
        $eventsHtml
        <p>See more in the calendar: ${calendarLink(Language.en)}</p>
        $unsubscribeEn
        """
                .trimIndent()
        )
    }

    override fun assistanceNeedPreschoolDecisionNotification(language: Language): EmailContent =
        assistanceNeedDecisionNotification(language) // currently same content
    override fun messageNotification(language: Language, thread: MessageThreadStub): EmailContent {
        val (typeFi, typeSv, typeEn) =
            when (thread.type) {
                MessageType.MESSAGE ->
                    if (thread.urgent) {
                        Triple(
                            "kiireellinen viesti",
                            "brådskande personligt meddelande",
                            "urgent message"
                        )
                    } else {
                        Triple("viesti", "personligt meddelande", "message")
                    }

                MessageType.BULLETIN ->
                    if (thread.urgent) {
                        Triple(
                            "kiireellinen tiedote",
                            "brådskande allmänt meddelande",
                            "urgent bulletin"
                        )
                    } else {
                        Triple("tiedote", "allmänt meddelande", "bulletin")
                    }
            }
        return EmailContent(
            subject = "Uusi $typeFi eVakassa / Nytt $typeSv i eVaka / New $typeEn in eVaka",
            text =
            """
                Sinulle on saapunut uusi $typeFi eVakaan. Lue viesti ${if (thread.urgent) "mahdollisimman pian " else ""}täältä: ${messageLink(Language.fi, thread.id)}
                
                Tämä on eVaka-järjestelmän automaattisesti lähettämä ilmoitus. Älä vastaa tähän viestiin.
                
                -----
                
                You have received a new $typeEn in eVaka. Read the message ${if (thread.urgent) "as soon as possible " else ""}here: ${messageLink(Language.en, thread.id)}
                
                This is an automatic message from the eVaka system. Do not reply to this message.  
            """
                .trimIndent(),
            html =
            """
                <p>Sinulle on saapunut uusi $typeFi eVakaan. Lue viesti ${if (thread.urgent) "mahdollisimman pian " else ""}täältä: ${messageLink(Language.fi, thread.id)}</p>
                <p>Tämä on eVaka-järjestelmän automaattisesti lähettämä ilmoitus. Älä vastaa tähän viestiin.</p>
                $unsubscribeFi
                <hr>
                
                <p>You have received a new $typeEn in eVaka. Read the message ${if (thread.urgent) "as soon as possible " else ""}here: ${messageLink(Language.en, thread.id)}</p>
                <p>This is an automatic message from the eVaka system. Do not reply to this message.</p>    
                $unsubscribeEn
            """
                .trimIndent()
        )
    }

    fun getDecisionEmailHtml(): String = """
        <p>Hei!</p>
        
        <p>Lapsellenne on tehty päätös.</p>
        
        <p>Päätös on nähtävissä eVakassa osoitteessa <a href="https://varhaiskasvatus.ouka.fi/">https://varhaiskasvatus.ouka.fi/</a>.</p>
        
        <p>Tähän viestiin ei voi vastata.</p>
        
        <hr>
        
        <p>Hello!</p>
        
        <p>A decision has been made for you by the Oulu early childhood education and care services.</p>
        
        <p>The decision can be seen online at <a href="https://varhaiskasvatus.ouka.fi/">https://varhaiskasvatus.ouka.fi/</a>.</p>
        
        <p>You may not reply to this message.</p>
        
    """.trimIndent()

    fun getDecisionEmailText(): String = """
        Hei!
        
        Lapsellenne on tehty päätös.
        
        Päätös on nähtävissä eVakassa osoitteessa https://varhaiskasvatus.ouka.fi/.
        
        Tähän viestiin ei voi vastata.
        
        ------------------------------------------------------------------------------
        
        Hello!
        
        A decision has been made for you by the Oulu early childhood education and care services. 
        
        The decision can be seen online at varhaiskasvatus.ouka.fi.
        
        You may not reply to this message.
        
    """.trimIndent()

    override fun missingReservationsNotification(
        language: Language,
        checkedRange: FiniteDateRange
    ): EmailContent {
        val start =
            checkedRange.start.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale("fi", "FI"))
            )
        return EmailContent(
            subject = "Läsnäolovarauksia puuttuu / There are missing attendance reservations",
            text =
            """
            Läsnäolovarauksia puuttuu seuraavalta viikolta: $start. Käythän merkitsemässä ne mahdollisimman pian.
            ----
            There are missing attendance reservations for the following week: $start. Please mark them as soon as possible.
            """
                .trimIndent(),
            html =
            """
            <p>Läsnäolovarauksia puuttuu seuraavalta viikolta: $start. Käythän merkitsemässä ne mahdollisimman pian.</p>
            $unsubscribeFi
            <hr>
            <p>There are missing attendance reservations for the following week: $start. Please mark them as soon as possible.</p>
            $unsubscribeEn
            """
                .trimIndent()
        )
    }

    fun getPendingDecisionEmailHtml(): String {
        return """
            <p>Hei!</p>
            <p>Sinulla on vastaamaton päätös Oulun varhaiskasvatukselta. Päätös tulee hyväksyä tai hylätä kahden viikon sisällä sen saapumisesta osoitteessa <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi</a> tai ottamalla yhteyttä päätöksessä mainittuun päiväkodin johtajaan.</p>
            <p>Tähän viestiin ei voi vastata.</p>
            $unsubscribeFi
            <hr>
            <p>Hello!</p>
            <p>A decision has been made for you by the Oulu early childhood education and care services and remains unanswered. The decision must be accepted or rejected within two weeks of its arrival online at <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi</a> or by contacting the daycare centre manager listed in the decision.</p> 
            <p>You may not reply to this message.</p>
            $unsubscribeEn
        """.trimIndent()
    }

    fun getPendingDecisionEmailText(): String {
        return """
            Hei! 

            Sinulla on vastaamaton päätös Oulun varhaiskasvatukselta. Päätös tulee hyväksyä tai hylätä kahden viikon sisällä sen saapumisesta osoitteessa varhaiskasvatus.ouka.fi tai ottamalla yhteyttä päätöksessä mainittuun päiväkodin johtajaan. 

            Tähän viestiin ei voi vastata.  
            
            ------------------------------------------------------------------------------
            
            Hello! 

            A decision has been made for you by the Oulu early childhood education and care services and remains unanswered. The decision must be accepted or rejected within two weeks of its arrival online at varhaiskasvatus.ouka.fi or by contacting the daycare centre manager listed in the decision. 

            You may not reply to this message.  
            
        """.trimIndent()
    }

    fun getClubApplicationReceivedEmailHtml(): String {
        return """
            <p>Hei!</p>
            
            <p>Olemme vastaanottaneet lapsenne hakemuksen avoimeen varhaiskasvatukseen. Pyydämme teitä olemaan yhteydessä suoraan toivomanne päiväkodin johtajaan ja tiedustelemaan vapaata avoimen varhaiskasvatuksen paikkaa.</p>
            
            <p>Hakemuksia käsitellään pääsääntöisesti vastaanottopäivämäärän mukaan. Sisarukset valitaan myös hakujärjestyksessä, ellei ole erityisperustetta.</p>
            
            <p>Päätös on nähtävissä ja hyväksyttävissä/hylättävissä <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi.</a></p>

            <p>
            Ystävällisesti <br/>
            Varhaiskasvatuksen palveluohjaus <br/>
            </p>
            
            <p>Tähän viestiin ei voi vastata.</p>
            
            <hr>
            
            <p>Hello!</p>
            
            <p>We have received your child’s application for open early childhood education and care. We request you to directly contact the manager of the daycare centre you wish to enrol in and inquire for free places in open early childhood education and care.</p>
            
            <p>The applications are usually processed in the order they are received. Siblings will also be enrolled in the order of application unless special ground exist.</p>
            
            <p>The decision may be viewed and accepted or rejected online at <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi.</a></p>
            
            <p>Yours, <br/>
            Early childhood education services coordination team <br/>
            </p>

            <p>You may not reply to this message.</p>
            
        """.trimIndent()
    }

    fun getClubApplicationReceivedEmailText(): String {
        return """
            Hei! 
            
            Olemme vastaanottaneet lapsenne hakemuksen avoimeen varhaiskasvatukseen. Pyydämme teitä olemaan yhteydessä suoraan toivomanne päiväkodin johtajaan ja tiedustelemaan vapaata avoimen varhaiskasvatuksen paikkaa. 
            
            Hakemuksia käsitellään pääsääntöisesti vastaanottopäivämäärän mukaan. Sisarukset valitaan myös hakujärjestyksessä, ellei ole erityisperustetta. 
            
            Päätös on nähtävissä ja hyväksyttävissä/hylättävissä varhaiskasvatus.ouka.fi.  
            
            Ystävällisesti,  
            
            Varhaiskasvatuksen palveluohjaus 
            
            Tähän viestiin ei voi vastata.
            
            ------------------------------------------------------------------------------
            
            Hello! 

            We have received your child’s application for open early childhood education and care. We request you to directly contact the manager of the daycare centre you wish to enrol in and inquire for free places in open early childhood education and care. 

            The applications are usually processed in the order they are received. Siblings will also be enrolled in the order of application unless special ground exist. 

            The decision may be viewed and accepted or rejected online at varhaiskasvatus.ouka.fi.  
            
            Yours, 

            Early childhood education services coordination team 
            
            You may not reply to this message. 
            
        """.trimIndent()
    }

    fun getDaycareApplicationReceivedEmailHtml(): String {
        return """
            <p>Hei!</p>
            
            <p>Lapsenne varhaiskasvatushakemus on vastaanotettu. Hakemuksen tehnyt huoltaja voi muokata hakemusta osoitteessa <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi</a> siihen saakka, kunnes palveluohjaus ottaa sen käsittelyyn. Varhaiskasvatuspaikan järjestelyaika on neljä kuukautta. Mikäli kyseessä on vanhemman äkillinen työllistyminen tai opintojen alkaminen, järjestelyaika on kaksi viikkoa. Toimittakaa tällöin työ- tai opiskelutodistus hakemuksen liitteeksi. Kahden viikon järjestelyaika alkaa todistuksen saapumispäivämäärästä. Jatketun aukiolon ja vuorohoidon palveluita järjestetään vanhempien vuorotyön perusteella.</p>
            
            <p><b>Mikäli lapsellenne järjestyy varhaiskasvatuspaikka jostakin hakemuksessa toivomastanne kunnallisesta varhaiskasvatuspaikasta</b>, ilmoitamme teille paikan viimeistään kaksi viikkoa ennen varhaiskasvatuksen toivottua aloitusajankohtaa. Muussa tapauksessa olemme teihin yhteydessä.</p>
            
            <p><b>Mikäli valitsitte ensimmäiseksi hakutoiveeksi yksityisen päiväkodin tai perhepäivähoitajan</b>, olkaa suoraan yhteydessä kyseiseen palveluntuottajaan varmistaaksenne varhaiskasvatuspaikan saamisen. Mikäli toivomanne palveluntuottaja ei pysty tarjoamaan varhaiskasvatuspaikkaa, pyydämme teitä olemaan yhteydessä varhaiskasvatuksen palveluohjaukseen.</p>
            
            <p><b>Siirtohakemukset</b> (lapsella on jo varhaiskasvatuspaikka Oulun kaupungin varhaiskasvatusyksikössä) käsitellään pääsääntöisesti hakemuksen saapumispäivämäärän mukaan. Merkittäviä syitä siirtoon ovat: aikaisemman varhaiskasvatuspaikan lakkauttaminen, sisarukset ovat eri yksiköissä, pitkä matka, huonot kulkuyhteydet, lapsen ikä, ryhmän ikärakenne, vuorohoidon tarpeen loppuminen sekä huomioon otettavat erityisperusteet.</p>
            
            <p><b>Mikäli ilmoititte hakemuksessa lapsenne tuen tarpeesta</b>, varhaiskasvatuksen erityisopettaja on teihin yhteydessä, jotta lapsen tuen tarpeet voidaan ottaa huomioon paikkaa osoitettaessa.</p>
            
            <p>Päätös on nähtävissä ja hyväksyttävissä/hylättävissä <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi.</a></p>
            
            <p>
            Ystävällisesti <br/>
            Varhaiskasvatuksen palveluohjaus <br/>
            </p>
            
            <p>Tähän viestiin ei voi vastata.</p>
            
            
            <hr>
            
            <p>Hello!</p>
            
            <p>The early childhood education and care application for your child has been received. The guardian who filed the application may edit it online at varhaiskasvatus.ouka.fi until such time as the service coordination team takes it up for processing. The time necessary to organize a place in early childhood education and care is four months. If care must begin earlier due to a parent’s sudden employment or beginning of their studies, the minimum time of notice is two weeks. In such a case, a certificate of employment or student status must be presented as an appendix to the application. The two weeks’ notice begins at the date this certificate is submitted. Extended opening hours and round-the-clock care services are provided if necessitated by the parents’ working hours.</p>
            
            <p>If placement in early childhood care and education can be offered for your child in one of the municipal early childhood education and care locations specified in your application, we will inform you of the location two before the intended start date at the latest. If not, we will contact you by telephone.</p>
            
            <p>If the first care location you picked is a private daycare centre or child minder, you should directly contact the service provider in question to ensure placement can be offered to you. If the service provider your picked is unable to offer you a place in care, we request you to contact the early childhood education and care services service counselling centre.</p>
            
            <p>Transfer applications (for children who are already enrolled in a City of Oulu early childhood education and care unit) will usually be processed in the order such applications are received. Acceptable reasons for transfer include: shutdown of the current care location, siblings enrolled in a different unit, a long distance, poor transportation connections, the age of the child, the age structure of the group, the end of a need for round-the-clock care, and other specific grounds to be considered individually.</p>
            
            <p>If you have specified a need for special support for your child in the application, a special needs early childhood education teacher will contact you in order to best consider your child’s need for support in making the enrolment decision.</p>
            
            <p>The decision may be viewed and accepted or rejected online at <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi</a></p>
            
            <p>Yours, <br/>
            Early childhood education services coordination team <br/>
            </p>
            
            <p>You may not reply to this message.</p>
            
        """.trimIndent()
    }

    fun getDaycareApplicationReceivedEmailText(): String {
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
            
            ------------------------------------------------------------------------------
            
            Hello! 
            
            The early childhood education and care application for your child has been received. The guardian who filed the application may edit it online at varhaiskasvatus.ouka.fi until such time as the service coordination team takes it up for processing. The time necessary to organize a place in early childhood education and care is four months. If care must begin earlier due to a parent’s sudden employment or beginning of their studies, the minimum time of notice is two weeks. In such a case, a certificate of employment or student status must be presented as an appendix to the application. The two weeks’ notice begins at the date this certificate is submitted. Extended opening hours and round-the-clock care services are provided if necessitated by the parents’ working hours. 

            If placement in early childhood care and education can be offered for your child in one of the municipal early childhood education and care locations specified in your application, we will inform you of the location two before the intended start date at the latest. If not, we will contact you by telephone.  

            If the first care location you picked is a private daycare centre or child minder, you should directly contact the service provider in question to ensure placement can be offered to you. If the service provider your picked is unable to offer you a place in care, we request you to contact the early childhood education and care services service counselling centre. 

            Transfer applications (for children who are already enrolled in a City of Oulu early childhood education and care unit) will usually be processed in the order such applications are received. Acceptable reasons for transfer include: shutdown of the current care location, siblings enrolled in a different unit, a long distance, poor transportation connections, the age of the child, the age structure of the group, the end of a need for round-the-clock care, and other specific grounds to be considered individually. 

            If you have specified a need for special support for your child in the application, a special needs early childhood education teacher will contact you in order to best consider your child’s need for support in making the enrolment decision.  

            The decision may be viewed and accepted or rejected online at varhaiskasvatus.ouka.fi.  

            Yours, 
            Early childhood education services coordination team 

            You may not reply to this message. 
            
        """.trimIndent()
    }

    fun getPreschoolApplicationReceivedEmailHtml(): String {
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
            
            <hr>
            
            <p>Hello!</p>
            
            <p>We have received your child’s registration for preschool education. The guardian who filed the application may edit it online until such time as the service coordination team takes it up for processing. The early childhood education services coordination team will enrol every child registered for preschool education with a preschool education unit during March. The decision may be viewed and accepted or rejected online at varhaiskasvatus.ouka.fi.</p>
            
            <p>If no preschool education group will be set up in the unit you have applied for, the coordination team will contact you and offer a spot in a unit where such a group will be set up.</p>
            
            <p>If you have specified a need for special support for your child in the application, a special needs early childhood education teacher will contact you in order to best consider your child’s need for support in making the enrolment decision.</p>
            
            <p>EARLY CHILDHOOD EDUCATION AND CARE IN CONJUNCTION WITH PRESCHOOL EDUCATION</p>
            
            <p>If you have applied for early childhood education and care services in conjunction with preschool education, please consider the following:</p>
            
            <ul><li>The time necessary to organize a place in early childhood education and care is four months. Extended opening hours and round-the-clock care services are provided if necessitated by the parents’ working hours or evening and/or weekend studies.</li>
            <li><b>If placement in early childhood care and education can be offered for your child in one of the municipal early childhood education and care locations specified in your application,</b> we will inform you of the location two before the intended start date at the latest. If not, we will contact you by telephone.</li>
            <li><b>If the first care location you picked is a private daycare centre,</b> you should directly contact the service provider in question to ensure placement can be offered to you. If the service provider your picked is unable to offer you a place in care, we request you to contact the early childhood education and care services service counselling centre.</li>
            <li><b>Transfer applications</b>  (for children who are already enrolled in a City of Oulu early childhood education and care unit) will usually be processed in the order such applications are received. Acceptable reasons for transfer include: shutdown of the current care location, siblings enrolled in a different unit, a long distance, poor transportation connections, the age of the child, the age structure of the group, the end of a need for round-the-clock care, and other specific grounds to be considered individually.</li>
            </ul>
            
            <p>The decision may be viewed and accepted or rejected online at <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi.</a></p>
            
            <p>The appendices to the application may be directly submitted with the online application through the eVaka service.</p>
            
            <p>Yours, <br/>
            Early childhood education services coordination team <br/>
            </p>
            
            <p>You may not reply to this message.</p>
        """.trimIndent()
    }

    fun getPreschoolApplicationReceivedEmailText(): String {
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
            
            ------------------------------------------------------------------------------
            
            Hello! 

            We have received your child’s registration for preschool education. The guardian who filed the application may edit it online until such time as the service coordination team takes it up for processing. The early childhood education services coordination team will enrol every child registered for preschool education with a preschool education unit during March. The decision may be viewed and accepted or rejected online at varhaiskasvatus.ouka.fi.  

            If no preschool education group will be set up in the unit you have applied for, the coordination team will contact you and offer a spot in a unit where such a group will be set up. 

            If you have specified a need for special support for your child in the application, a special needs early childhood education teacher will contact you in order to best consider your child’s need for support in making the enrolment decision.  

            EARLY CHILDHOOD EDUCATION AND CARE IN CONJUNCTION WITH PRESCHOOL EDUCATION             
            
            If you have applied for early childhood education and care services in conjunction with preschool education, please consider the following: 

            - The time necessary to organize a place in early childhood education and care is four months. Extended opening hours and round-the-clock care services are provided if necessitated by the parents’ working hours or evening and/or weekend studies. 

            - If placement in early childhood care and education can be offered for your child in one of the municipal early childhood education and care locations specified in your application, we will inform you of the location two before the intended start date at the latest. If not, we will contact you by telephone.  

            - If the first care location you picked is a private daycare centre, you should directly contact the service provider in question to ensure placement can be offered to you. If the service provider your picked is unable to offer you a place in care, we request you to contact the early childhood education and care services service counselling centre. 

            - Transfer applications (for children who are already enrolled in a City of Oulu early childhood education and care unit) will usually be processed in the order such applications are received. Acceptable reasons for transfer include: shutdown of the current care location, siblings enrolled in a different unit, a long distance, poor transportation connections, the age of the child, the age structure of the group, the end of a need for round-the-clock care, and other specific grounds to be considered individually. 

            The decision may be viewed and accepted or rejected online at varhaiskasvatus.ouka.fi.         

            The appendices to the application may be directly submitted with the online application through the eVaka service.  

            Yours, 
            Early childhood education services coordination team 
            
            You may not reply to this message. 
            
        """.trimIndent()
    }

    override fun assistanceNeedDecisionNotification(language: Language): EmailContent {
        return EmailContent(subjectForDecisionEmail, getDecisionEmailText(), getDecisionEmailHtml())
    }

    override fun pendingDecisionNotification(language: Language): EmailContent {
        return EmailContent(
            subjectForPendingDecisionEmail,
            getPendingDecisionEmailText(),
            getPendingDecisionEmailHtml()
        )
    }

    override fun clubApplicationReceived(language: Language): EmailContent {
        return EmailContent(
            subjectForClubApplicationReceivedEmail,
            getClubApplicationReceivedEmailText(),
            getClubApplicationReceivedEmailHtml()
        )
    }

    override fun daycareApplicationReceived(language: Language): EmailContent {
        return EmailContent(
            subjectForDaycareApplicationReceivedEmail,
            getDaycareApplicationReceivedEmailText(),
            getDaycareApplicationReceivedEmailHtml()
        )
    }

    override fun preschoolApplicationReceived(language: Language, withinApplicationPeriod: Boolean): EmailContent {
        return EmailContent(
            subjectForPreschoolApplicationReceivedEmail,
            getPreschoolApplicationReceivedEmailText(),
            getPreschoolApplicationReceivedEmailHtml()
        )
    }

    override fun childDocumentNotification(language: Language, childId: ChildId): EmailContent {
        return EmailContent.fromHtml(
            subject = "Uusi dokumentti eVakassa / Nytt dokument i eVaka / New document in eVaka",
            html =
            """
                <p>Sinulle on saapunut uusi dokumentti eVakaan. Lue dokumentti täältä: ${childLink(Language.fi, childId)}</p>
                <p>Tämä on eVaka-järjestelmän automaattisesti lähettämä ilmoitus. Älä vastaa tähän viestiin.</p>
                $unsubscribeFi
                <hr>
                <p>Du har fått ett nytt dokument i eVaka. Läs dokumentet här: ${childLink(Language.sv, childId)}</p>
                <p>Detta besked skickas automatiskt av eVaka. Svara inte på detta besked.</p>
                $unsubscribeSv
                <hr>
                <p>You have received a new eVaka document. Read the document here: ${childLink(Language.en, childId)}</p>
                <p>This is an automatic message from the eVaka system. Do not reply to this message.</p>
                $unsubscribeEn
"""
        )
    }

    override fun vasuNotification(language: Language, childId: ChildId): EmailContent {
        return childDocumentNotification(language, childId)
    }
    override fun pedagogicalDocumentNotification(language: Language, childId: ChildId): EmailContent {
        return EmailContent(
            subject = "Uusi pedagoginen dokumentti eVakassa / New pedagogical document in eVaka",
            text =
            """
                Sinulle on saapunut uusi pedagoginen dokumentti eVakaan. Lue dokumentti täältä: ${childLink(Language.fi, childId)}
                
                Tämä on eVaka-järjestelmän automaattisesti lähettämä ilmoitus. Älä vastaa tähän viestiin.
                
                -----
                
                You have received a new eVaka pedagogical document. Read the document here:  ${childLink(Language.en, childId)}
                
                This is an automatic message from the eVaka system. Do not reply to this message.  
            """
                .trimIndent(),
            html =
            """
                <p>Sinulle on saapunut uusi pedagoginen dokumentti eVakaan. Lue dokumentti täältä: ${childLink(Language.fi, childId)}</p>
                <p>Tämä on eVaka-järjestelmän automaattisesti lähettämä ilmoitus. Älä vastaa tähän viestiin.</p>
                $unsubscribeFi
                <hr>
                
                <p>You have received a new eVaka pedagogical document. Read the document here: ${childLink(Language.en, childId)}</p>
                <p>This is an automatic message from the eVaka system. Do not reply to this message.</p>
                $unsubscribeEn
            """
                .trimIndent()
        )
    }

    override fun outdatedIncomeNotification(
        notificationType: IncomeNotificationType,
        language: Language
    ): EmailContent {
        return when (notificationType) {
            IncomeNotificationType.INITIAL_EMAIL -> outdatedIncomeNotificationInitial()
            IncomeNotificationType.REMINDER_EMAIL -> outdatedIncomeNotificationReminder()
            IncomeNotificationType.EXPIRED_EMAIL -> outdatedIncomeNotificationExpired()
        }
    }

    fun outdatedIncomeNotificationInitial(): EmailContent {
        return EmailContent(
            subject = "Tulotietojen tarkastus- kehotus / Request to review income information",
            text =
            """
                Hyvä asiakkaamme
                
                Varhaiskasvatuksen asiakasmaksun tai palvelusetelin omavastuuosuuden perusteena olevat tulotiedot tulotietonne ovat vanhentumassa.
                
                Pyydämme toimittamaan tuloselvityksen eVakassa 14 päivän kuluessa tästä ilmoituksesta.
                
                Mikäli ette toimita uusia tulotietoja, asiakasmaksu määräytyy korkeimman maksun mukaisesti.
                
                Lisätietoja saatte tarvittaessa: varhaiskasvatusmaksut@ouka.fi
                
                Tulotiedot:  ${incomeLink(Language.fi)}
                
                Tämä on eVaka-järjestelmän automaattisesti lähettämä ilmoitus. Älä vastaa tähän viestiin.
                
                -----

                Dear client
                
                The income information used for determining the early childhood education fee or the out-of-pocket cost of a service voucher is reviewed when income information is out of date.
                
                We ask you to submit your income statement through eVaka within 14 days of this notification.
                
                If you do not provide your latest income information, your client fee will be determined based on the highest fee.
                
                Inquiries: varhaiskasvatusmaksut@ouka.fi

                Income information:  ${incomeLink(Language.en)}

                This is an automatic message from the eVaka system. Do not reply to this message.
            """
                .trimIndent(),
            html =
            """
                <p>Hyvä asiakkaamme</p>
                <p>Varhaiskasvatuksen asiakasmaksun tai palvelusetelin omavastuuosuuden perusteena olevat tulotietonne ovat vanhentumassa.</p>
                <p>Pyydämme toimittamaan tuloselvityksen eVakassa 14 päivän kuluessa tästä ilmoituksesta.</p>
                <p>Mikäli ette toimita uusia tulotietoja, asiakasmaksu määräytyy korkeimman maksun mukaisesti.</p>
                <p>Lisätietoja saatte tarvittaessa: varhaiskasvatusmaksut@ouka.fi</p>
                <p>Tulotiedot:  ${incomeLink(Language.fi)}</p>
                <p>Tämä on eVaka-järjestelmän automaattisesti lähettämä ilmoitus. Älä vastaa tähän viestiin.</p>
                $unsubscribeFi
                <hr>
                <p>Dear client</p>
                <p>The income information used for determining the early childhood education fee or the out-of-pocket cost of a service voucher is reviewed when income information is out of date.</p>
                <p>We ask you to submit your income statement through eVaka within 14 days of this notification.</p>
                <p>If you do not provide your latest income information, your client fee will be determined based on the highest fee.</p>
                <p>Inquiries: varhaiskasvatusmaksut@ouka.fi</p>
                <p>Income information:  ${incomeLink(Language.en)}</p>
                <p>This is an automatic message from the eVaka system. Do not reply to this message.</p>
                $unsubscribeEn
            """
                .trimIndent()
        )
    }

    fun outdatedIncomeNotificationReminder(): EmailContent {
        return EmailContent(
            subject = "Tulotietojen tarkastus- kehotus / Request to review income information",
            text =
            """
                Hyvä asiakkaamme
                
                Ette ole vielä toimittaneet uusia tulotietoja. Varhaiskasvatuksen asiakasmaksun tai palvelusetelin omavastuuosuuden perusteena olevat tulotiedot tarkistetaan vuosittain.
                
                Pyydämme toimittamaan tuloselvityksen eVakassa 7 päivän kuluessa tästä ilmoituksesta. eVakassa voitte myös antaa suostumuksen korkeimpaan maksuluokkaan tai tulorekisterin käyttöön.
                
                Mikäli ette toimita uusia tulotietoja, asiakasmaksu määräytyy korkeimman maksuluokan mukaan.
                
                Lisätietoja saatte tarvittaessa: varhaiskasvatusmaksut@ouka.fi
                
                Tulotiedot: ${incomeLink(Language.fi)}
                
                Tämä on eVaka-järjestelmän automaattisesti lähettämä ilmoitus. Älä vastaa tähän viestiin.
                
                -----

                Dear client
                
                You have not yet submitted your latest income information. The income information used for determining the early childhood education fee or the out-of-pocket cost of a service voucher is reviewed every year.
                
                We ask you to submit your income statement through eVaka within 7 days of this notification. Through eVaka, you can also give your consent to the highest fee or the use of the Incomes Register.
                
                If you do not provide your latest income information, your client fee will be determined based on the highest fee category.
                
                Inquiries: varhaiskasvatusmaksut@ouka.fi
                
                Income information: ${incomeLink(Language.en)}
                
                This is an automatic message from the eVaka system. Do not reply to this message.
            """
                .trimIndent(),
            html =
            """
                <p>Hyvä asiakkaamme</p>
                <p>Ette ole vielä toimittaneet uusia tulotietoja. Varhaiskasvatuksen asiakasmaksun tai palvelusetelin omavastuuosuuden perusteena olevat tulotiedot tarkistetaan vuosittain.</p>
                <p>Pyydämme toimittamaan tuloselvityksen eVakassa 7 päivän kuluessa tästä ilmoituksesta. eVakassa voitte myös antaa suostumuksen korkeimpaan maksuluokkaan tai tulorekisterin käyttöön.</p>
                <p>Mikäli ette toimita uusia tulotietoja, asiakasmaksu määräytyy korkeimman maksuluokan mukaan.</p>
                <p>Lisätietoja saatte tarvittaessa: varhaiskasvatusmaksut@ouka.fi</p>
                <p>Tulotiedot: ${incomeLink(Language.fi)}</p>
                <p>Tämä on eVaka-järjestelmän automaattisesti lähettämä ilmoitus. Älä vastaa tähän viestiin.</p>
                $unsubscribeFi
                <hr>
                <p>Dear client</p>
                <p>You have not yet submitted your latest income information. The income information used for determining the early childhood education fee or the out-of-pocket cost of a service voucher is reviewed every year.</p>
                <p>We ask you to submit your income statement through eVaka within 7 days of this notification. Through eVaka, you can also give your consent to the highest fee or the use of the Incomes Register.</p>
                <p>If you do not provide your latest income information, your client fee will be determined based on the highest fee category.</p>
                <p>Inquiries: varhaiskasvatusmaksut@ouka.fi</p>
                <p>Income information: ${incomeLink(Language.en)}</p>
                <p>This is an automatic message from the eVaka system. Do not reply to this message.</p>
                $unsubscribeEn
            """
                .trimIndent()
        )
    }

    fun outdatedIncomeNotificationExpired(): EmailContent {
        return EmailContent(
            subject = "Tulotietojen tarkastus- kehotus / Request to review income information",
            text =
            """
                Hyvä asiakkaamme
                
                Seuraava asiakasmaksunne määräytyy korkeimman maksuluokan mukaan, sillä ette ole toimittaneet uusia tulotietoja määräaikaan mennessä.
                
                Lisätietoja saatte tarvittaessa: varhaiskasvatusmaksut@ouka.fi
                
                Tämä on eVaka-järjestelmän automaattisesti lähettämä ilmoitus. Älä vastaa tähän viestiin.
                
                -----
                
                Dear client
                
                Your next client fee will be determined based on the highest fee category as you did not provide your latest income information by the deadline.
                
                Inquiries: varhaiskasvatusmaksut@ouka.fi

                This is an automatic message from the eVaka system. Do not reply to this message.  
            """
                .trimIndent(),
            html =
            """
                <p>Hyvä asiakkaamme</p>
                <p>Seuraava asiakasmaksunne määräytyy korkeimman maksuluokan mukaan, sillä ette ole toimittaneet uusia tulotietoja määräaikaan mennessä.</p>
                <p>Lisätietoja saatte tarvittaessa: varhaiskasvatusmaksut@ouka.fi</p>
                <p>Tämä on eVaka-järjestelmän automaattisesti lähettämä ilmoitus. Älä vastaa tähän viestiin.</p>
                $unsubscribeFi
                <hr>
                <p>Dear client</p>
                <p>Your next client fee will be determined based on the highest fee category as you did not provide your latest income information by the deadline.</p>
                <p>Inquiries: varhaiskasvatusmaksut@ouka.fi</p>
                <p>This is an automatic message from the eVaka system. Do not reply to this message.</p>
                $unsubscribeEn
            """
                .trimIndent()
        )
    }
}
