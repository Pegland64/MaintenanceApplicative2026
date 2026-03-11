package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (items[i].name.equals("Aged Brie")) {
                doAgedBrie(items[i]);
            } else if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                doBackstagePass(items[i]);
            } else if (items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                doSulfuras(items[i]);
            } else {
                doNormalItem(items[i]);
            }
        }
    }

    private void doAgedBrie(Item item) {
        item.sellIn -= 1;
        if (item.quality < 50) item.quality += 1;
        if (item.sellIn < 0 && item.quality < 50) item.quality += 1;
    }

    private void doBackstagePass(Item item) {
        item.sellIn -= 1;
        if (item.sellIn < 0) {
            item.quality = 0;
            return;
        }
        if (item.quality < 50) item.quality += 1;
        if (item.sellIn < 10 && item.quality < 50) item.quality += 1;
        if (item.sellIn < 5  && item.quality < 50) item.quality += 1;
    }

    private void doSulfuras(Item item) {
        // Sulfuras never changes
    }

    private void doNormalItem(Item item) {
        item.sellIn -= 1;
        if (item.quality > 0) item.quality -= 1;
        if (item.sellIn < 0 && item.quality > 0) item.quality -= 1;
    }
}
