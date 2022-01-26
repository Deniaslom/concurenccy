package ru.clevertec.task.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListImplTest {
    Logger logger = LoggerFactory.getLogger(MyArrayListImplTest.class);

    private List<String> list = new MyArrayListImpl<>();

    private static final String STRING_0 = "String 0";
    private static final String STRING_1 = "String 1";
    private static final String STRING_2 = "String 2";
    private static final String STRING_3 = "String 3";
    private static final String STRING_4 = "String 4";
    private static final String STRING_5 = "String 5";
    private static final String STRING_6 = "String 6";

    @BeforeEach
    public void start() {
        list.add(STRING_0);
        list.add(STRING_1);
        list.add(STRING_2);
        list.add(STRING_3);
    }


    @Test
    public void checkTrueMethodContains() {
        assertAll(() -> assertTrue(list.contains(STRING_0)),
                  () -> assertTrue(list.contains(STRING_1)),
                  () -> assertTrue(list.contains(STRING_2)),
                  () -> assertTrue(list.contains(STRING_3))
        );
    }

    @Test
    public void checkFalseMethodContains() {
        assertFalse(list.contains(STRING_6));
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
    public void checkAddAndContains() {
        list.add("String 10");
        assertTrue(list.contains("String 10"));
    }

    @Test
    public void addByIndex(){
        list.add(0, "String index 0");
        list.add(2, "String index 2");
        list.add(4, "String index 4");

        assertAll(
                () -> assertTrue(list.get(0).equals("String index 0")),
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

        assertAll(
                () -> assertTrue(list.get(0).equals("set 0")),
                () -> assertTrue(list.get(1).equals("set 1")),
                () -> assertTrue(list.get(2).equals("set 2")),
                () -> assertTrue(list.get(3).equals("set 3"))
        );
    }

    @Test
    public void checkIndexOf(){
        assertAll(
                () -> assertTrue(list.indexOf(STRING_0) == 0),
                () -> assertTrue(list.indexOf(STRING_1) == 1),
                () -> assertTrue(list.indexOf(STRING_2) == 2),
                () -> assertTrue(list.indexOf(STRING_3) == 3),
                () -> assertFalse(list.indexOf(STRING_4) == 4)
        );
    }

    @Test
    public void checkLastIndexOf(){
        list.add(STRING_2);
        assertTrue(list.lastIndexOf(STRING_2) == 4);
    }

    @Test
    public void checkSizeAndGetAfterRemoveByObjects(){
        list.remove(STRING_0);
        list.remove(STRING_1);
        assertTrue(list.size() == 2);
        assertTrue(list.get(0).equals(STRING_2));
        assertTrue(list.get(1).equals(STRING_3));
    }

    @Test
    public void checkForEach(){
        for(String str : list){
            logger.info(str);
        }
    }
}