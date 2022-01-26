package ru.clevertec.task.collection.customCollections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {
    private List<String> list = new CustomArrayList<>();

    @BeforeEach
    public void start() {
        list.add("String 0");
        list.add("String 1");
        list.add("String 2");
        list.add("String 3");
    }


    @Test
    public void checkTrueMethodContains() {
        assertAll(() -> assertTrue(list.contains("String 0")),
                  () -> assertTrue(list.contains("String 1")),
                  () -> assertTrue(list.contains("String 2")),
                  () -> assertTrue(list.contains("String 3"))
        );
    }

    @Test
    public void checkFalseMethodContains() {
        assertFalse(list.contains("String 6"));
    }

    @Test
    public void checkSize() {
        assertTrue(list.size() == 4);
    }

    @Test
    public void checkSizeAfterRemoveElement() {
        list.remove(1);
        assertTrue(list.size() == 3);
    }

    @Test
    public void checkSizeAfterAddElement() {
        list.add("String add 1");
        list.add("String add 2");
        assertTrue(list.size() == 6);
    }

    @Test
    public void checkIsEmpty() {
        assertFalse(list.isEmpty());
    }

    @Test
    public void checkIsEmptyAfterClear() {
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void checkAddAndContains() {
        list.add("String 10");
        assertTrue(list.contains("String 10"));
    }

    @Test
    public void addByIndex(){
        list.add(0, "String index 0");
        list.add(2, "String index 2");
        list.add(4, "String index 4");

        assertAll(() -> assertTrue(list.get(0).equals("String index 0")),
                () -> assertTrue(list.get(2).equals("String index 2")),
                () -> assertTrue(list.get(4).equals("String index 4")),
                () -> assertTrue(list.get(6).equals("String 3"))
        );
    }

    @Test
    public void checkSetElement(){
        list.set(0, "set 0");
        list.set(1, "set 1");
        list.set(2, "set 2");
        list.set(3, "set 3");

        assertAll(() -> assertTrue(list.get(0).equals("set 0")),
                () -> assertTrue(list.get(1).equals("set 1")),
                () -> assertTrue(list.get(2).equals("set 2")),
                () -> assertTrue(list.get(3).equals("set 3"))
        );
    }

    @Test
    public void checkIndexOf(){
        assertAll(() -> assertTrue(list.indexOf("String 0") == 0),
                () -> assertTrue(list.indexOf("String 1") == 1),
                () -> assertTrue(list.indexOf("String 2") == 2),
                () -> assertTrue(list.indexOf("String 3") == 3),
                () -> assertFalse(list.indexOf("String 4") == 4)
        );
    }

    @Test
    public void checkLastIndexOf(){
        list.add("String 2");
        assertTrue(list.lastIndexOf("String 2") == 4);
    }

    @Test
    public void checkSizeAndGetAfterRemoveByObjects(){
        list.remove("String 0");
        list.remove("String 1");
        assertTrue(list.size() == 2);
        assertTrue(list.get(0).equals("String 2"));
        assertTrue(list.get(1).equals("String 3"));
    }

    @Test
    public void checkForEach(){
        for(String str : list){
            System.out.println(str);
        }
    }
}