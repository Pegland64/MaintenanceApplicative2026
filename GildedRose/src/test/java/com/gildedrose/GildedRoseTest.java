package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void normalItem_qualityDegradesByOne_beforeSellIn() {
        Item[] items = { new Item("foo", 5, 10) };
        new GildedRose(items).updateQuality();
        assertEquals(4, items[0].sellIn);
        assertEquals(9, items[0].quality);
    }

    @Test
    void normalItem_qualityDegradesByTwo_afterSellIn() {
        Item[] items = { new Item("foo", 0, 10) };
        new GildedRose(items).updateQuality();
        assertEquals(-1, items[0].sellIn);
        assertEquals(8, items[0].quality);
    }

    @Test
    void normalItem_qualityNeverNegative_beforeSellIn() {
        Item[] items = { new Item("foo", 5, 0) };
        new GildedRose(items).updateQuality();
        assertEquals(0, items[0].quality);
    }

    @Test
    void normalItem_qualityNeverNegative_afterSellIn() {
        Item[] items = { new Item("foo", 0, 0) };
        new GildedRose(items).updateQuality();
        assertEquals(0, items[0].quality);
    }

    @Test
    void normalItem_qualityOne_afterSellIn_degradesToZero() {
        Item[] items = { new Item("foo", 0, 1) };
        new GildedRose(items).updateQuality();
        assertEquals(0, items[0].quality);
    }

    @Test
    void agedBrie_qualityIncreasesBeforeSellIn() {
        Item[] items = { new Item("Aged Brie", 5, 20) };
        new GildedRose(items).updateQuality();
        assertEquals(21, items[0].quality);
    }

    @Test
    void agedBrie_qualityIncreasesAfterSellIn() {
        Item[] items = { new Item("Aged Brie", 0, 20) };
        new GildedRose(items).updateQuality();
        assertEquals(22, items[0].quality);
    }

    @Test
    void agedBrie_qualityNeverExceeds50_beforeSellIn() {
        Item[] items = { new Item("Aged Brie", 5, 50) };
        new GildedRose(items).updateQuality();
        assertEquals(50, items[0].quality);
    }

    @Test
    void agedBrie_qualityNeverExceeds50_afterSellIn() {
        Item[] items = { new Item("Aged Brie", 0, 50) };
        new GildedRose(items).updateQuality();
        assertEquals(50, items[0].quality);
    }

    @Test
    void agedBrie_qualityAt49_afterSellIn_capsAt50() {
        Item[] items = { new Item("Aged Brie", 0, 49) };
        new GildedRose(items).updateQuality();
        assertEquals(50, items[0].quality);
    }

    @Test
    void sulfuras_neverChanges() {
        Item[] items = { new Item("Sulfuras, Hand of Ragnaros", 5, 80) };
        new GildedRose(items).updateQuality();
        assertEquals(5,  items[0].sellIn);
        assertEquals(80, items[0].quality);
    }

    @Test
    void sulfuras_neverChanges_pastSellIn() {
        Item[] items = { new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
        new GildedRose(items).updateQuality();
        assertEquals(-1, items[0].sellIn);
        assertEquals(80, items[0].quality);
    }

    @Test
    void backstagePass_qualityIncreasesByOne_moreThan10DaysLeft() {
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        new GildedRose(items).updateQuality();
        assertEquals(21, items[0].quality);
    }

    @Test
    void backstagePass_qualityIncreasesByTwo_10DaysLeft() {
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20) };
        new GildedRose(items).updateQuality();
        assertEquals(22, items[0].quality);
    }

    @Test
    void backstagePass_qualityIncreasesByTwo_between6And10Days() {
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 7, 20) };
        new GildedRose(items).updateQuality();
        assertEquals(22, items[0].quality);
    }

    @Test
    void backstagePass_qualityIncreasesByThree_5DaysLeft() {
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20) };
        new GildedRose(items).updateQuality();
        assertEquals(23, items[0].quality);
    }

    @Test
    void backstagePass_qualityIncreasesByThree_1DayLeft() {
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 1, 20) };
        new GildedRose(items).updateQuality();
        assertEquals(23, items[0].quality);
    }

    @Test
    void backstagePass_qualityDropsToZero_afterConcert() {
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 40) };
        new GildedRose(items).updateQuality();
        assertEquals(0, items[0].quality);
    }

    @Test
    void backstagePass_qualityNeverExceeds50_moreThan10Days() {
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 50) };
        new GildedRose(items).updateQuality();
        assertEquals(50, items[0].quality);
    }

    @Test
    void backstagePass_qualityNeverExceeds50_10DaysLeft() {
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49) };
        new GildedRose(items).updateQuality();
        assertEquals(50, items[0].quality);
    }

    @Test
    void backstagePass_qualityNeverExceeds50_5DaysLeft() {
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49) };
        new GildedRose(items).updateQuality();
        assertEquals(50, items[0].quality);
    }

    @Test
    void backstagePass_qualityNeverExceeds50_5DaysLeft_startAt48() {
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48) };
        new GildedRose(items).updateQuality();
        assertEquals(50, items[0].quality);
    }

    @Test
    void item_toStringFormat() {
        Item item = new Item("foo", 3, 7);
        assertEquals("foo, 3, 7", item.toString());
    }
}
