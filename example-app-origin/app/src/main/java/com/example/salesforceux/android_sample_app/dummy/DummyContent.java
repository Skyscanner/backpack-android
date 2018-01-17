package com.example.salesforceux.android_sample_app.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyContent {

    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {
        DummyItem item1 = new DummyItem("Burlington Textiles Corp of America", "NC", "(336) 222-7000", "Customer - Direct", "Joe Green", "CD656092", "Apparel", "www.burlington.com", "1");
        ITEMS.add(item1);
        ITEM_MAP.put("1", item1);

        DummyItem item2 = new DummyItem("Dickenson Plc", "KS", "(785) 241-6200", "Customer - Direct", "Joe Green", "CC634267", "Consulting", "www.dickenson-consulting.com", "2");
        ITEMS.add(item2);
        ITEM_MAP.put("2", item2);

        DummyItem item3 = new DummyItem("Edge Communications", "TX", "(512) 757-6000", "Customer - Direct", "Joe Green", "CD874587", "Electronics", "www.edgecomm.com", "3");
        ITEMS.add(item3);
        ITEM_MAP.put("3", item3);

        DummyItem item4 = new DummyItem("Express Logistics and Transport", "OR", "(503) 421-7800", "Customer - Direct", "Joe Green", "CD876345", "Transportation", "www.expresslandt.com", "4");
        ITEMS.add(item4);
        ITEM_MAP.put("4", item4);

        DummyItem item5 = new DummyItem("GenePoint", "CA", "(650) 867-3450", "Customer - Channel", "Joe Green", "CC300283", "Biotechnology", "www.genepoint.com", "5");
        ITEMS.add(item5);
        ITEM_MAP.put("5", item5);
    }

    public static class DummyItem {

        public final String name;
        public final String state;
        public final String phone;
        public final String type;
        public final String owner;

        public final String number;
        public final String industry;
        public final String website;

        public final String id;

        public DummyItem(String name, String state, String phone, String type, String owner, String number, String industry, String website, String id) {
            this.name = name;
            this.owner = owner;
            this.state = state;
            this.type = type;
            this.phone = phone;

            this.number = number;
            this.industry = industry;
            this.website = website;

            this.id = id;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
