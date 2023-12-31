package bsuir.labwork.Labwork.utils;

import bsuir.labwork.Labwork.models.CreditCard;
import bsuir.labwork.Labwork.interfaces.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class SAXParser implements Parser {
    @Override
    public List<CreditCard> parseCreditCards(String filePath) throws Exception {
        List<CreditCard> cards = new ArrayList<>();
        javax.xml.parsers.SAXParserFactory factory = SAXParserFactory.newInstance();
        javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {
            CreditCard card;
            String currentElement;

            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                currentElement = qName;
                if ("credit_card".equals(currentElement)) {
                    card = new CreditCard();
                }
            }

            public void endElement(String uri, String localName, String qName) {
                if ("credit_card".equals(qName)) {
                    cards.add(card);
                }
                currentElement = "";
            }

            public void characters(char[] ch, int start, int length) {
                String value = new String(ch, start, length).trim();
                if (value.isEmpty()) return;

                if ("card_number".equals(currentElement)) {
                    card.setCardNumber(value);
                } else if ("card_holder".equals(currentElement)) {
                    card.setCardHolder(value);
                } else if ("expiration_date".equals(currentElement)) {
                    card.setExpirationDate(value);
                } else if ("cvc".equals(currentElement)) {
                    card.setCvc(value);
                }
            }
        };

        saxParser.parse(filePath, handler);
        return cards;
    }
}
