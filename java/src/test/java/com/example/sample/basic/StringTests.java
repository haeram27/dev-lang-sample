package com.example.sample.basic;

import org.junit.jupiter.api.Test;

import com.example.sample.EvaluatedTimeTests;

public class StringTests extends EvaluatedTimeTests {

    String textblk1 = """
                      <html>
                          <body>
                              <p>Hello, World</p>
                          </body>
                      </html>
                      """;

    String textblk2 = """
                      <html>
                          <body>
                              <p>Hello, World</p>
                          </body>
                      </html>"""; // no empty line at end of block

    String textblk3 = """
                      <html>
                          <body>
                              <p>Hello, World</p>
                          </body>
                </html>
                      """; // block lines start from start of closing """

    String textblk4 = """
                      <html>
                          <body>
                              <p>Hello, World</p>
                          </body>
                      </html>
"""; // block lines start from start of closing """ (0) so all preceeding space will be existed

/*
        - start of line is most left character place in a block after open """
        - textblk1 or textblk2 is recommended

Result:

=== textblk1 ============================================================
<html>
    <body>
        <p>Hello, World</p>
    </body>
</html>

=== textblk2 ============================================================
<html>
    <body>
        <p>Hello, World</p>
    </body>
</html>
=== textblk3 ============================================================
      <html>
          <body>
              <p>Hello, World</p>
          </body>
</html>

=== textblk4 ============================================================
                      <html>
                          <body>
                              <p>Hello, World</p>
                          </body>
                      </html>
*/
    @Test
    void stringTest() {
        System.out.println("=== textblk1 ============================================================");
        System.out.println(textblk1);
        System.out.println("=== textblk2 ============================================================");
        System.out.println(textblk2);
        System.out.println("=== textblk3 ============================================================");
        System.out.println(textblk3);
        System.out.println("=== textblk4 ============================================================");
        System.out.println(textblk4);
    }
}
