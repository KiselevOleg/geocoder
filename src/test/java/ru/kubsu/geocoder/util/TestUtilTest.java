package ru.kubsu.geocoder.util;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TestUtilTest {
  @AfterAll
  static void afterAll() {
    //System.out.println("after all");
  }
  @BeforeAll
  static void beforeAll() {
    //System.out.println("before all");
  }

  @BeforeEach
  void setUp() {
    //System.out.println("before each");
  }
  @AfterEach
  void tearDown() {
    //System.out.println("after each");
  }

  @Test
  void test1() {
    //System.out.println("test1");
    throw new IllegalArgumentException();
  }
  @Test
  void test2() {
    //System.out.println("test2");
    fail();
  }
  @Test
  void test3() {
    //System.out.println("test2");
    assertEquals(4, 2+2);
    //assert 2>3;
  }
  @Test
  void test4() {
        /*int actial = TestUtil.sum(1,2);
        int expected = 3;
        assertEquals(expected, actial);*/

    Assertions.assertEquals(3, ru.kubsu.geocoder.util.TestUtil.sum(1,2));
    Assertions.assertEquals(300, ru.kubsu.geocoder.util.TestUtil.sum(99,201));
    Assertions.assertEquals(-7, ru.kubsu.geocoder.util.TestUtil.sum(-1,-6));
    Assertions.assertEquals(0, ru.kubsu.geocoder.util.TestUtil.sum(-4,4));
    Assertions.assertEquals(1, ru.kubsu.geocoder.util.TestUtil.sum(3000,-2999));
  }
}
