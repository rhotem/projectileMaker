package me.justacat.ArcaneProjectiles.gui;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PagesGUI {


    private int spaces;
    private List<ItemStack> items;


    public PagesGUI(int itemsPerPage, List<ItemStack> items) {
        spaces = itemsPerPage;
        this.items = items;
    }

    //spaces = item in page
    public List<ItemStack> getPageItems(int page) {

        int upperBound = page * spaces;
        int lowerBound = upperBound - spaces;

        List<ItemStack> newItems = new ArrayList<>();
        for (int i = lowerBound; i < upperBound; i++) {

            try {
                newItems.add(items.get(i));
            } catch (IndexOutOfBoundsException e) {
                break;
            }

        }

        return newItems;
    }


    public boolean isPageValid(int page) {

        if (page <= 0) {return false;}


        // for upperBound <, and for lowerBound >=;
        int upperBound = page * spaces;
        int lowerBound = upperBound - spaces;

        return items.size() > lowerBound;

    }





}
