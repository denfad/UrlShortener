package ru.denfad.UrlShortener.service;


public class Base62Coder {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGHT = 7;

    protected static String encode(int num) {
      StringBuffer buffer = new StringBuffer();
      int i;

      while (num != 0) {
            i = num % 62;
            buffer.append(ALPHABET.charAt(i));
            num /= 62;
      }



      while(buffer.length() < LENGHT) {
          buffer.append('a');
      }
      return buffer.reverse().toString();
  }

  protected static int decode(String line) {
        int result = 0;
        for(char c: line.toCharArray()){
            result *= 62;
            int num = ALPHABET.indexOf(c);
            result += num;
        }




        return result;
  }


}
