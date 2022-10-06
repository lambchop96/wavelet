import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
  String stringPool[] = new String[100];
  int size = 0;

  public String handleRequest(URI url) {
    if (url.getPath().equals("/")) {
      String stringList = "";

      for (int idx = 0; idx < size; idx++) {
        stringList += stringPool[idx] + '\n';
      }

      return stringList;
    }
    else {
      System.out.println("Path: " + url.getPath());

      if (url.getPath().contains("/add")) {
        String parameters[] = url.getQuery().split("=");
        
        if (parameters[0].equals("s")) { // paramters[0] is the left side of the '='
          stringPool[size] = parameters[1];
          size++;
          
          return String.format("New string added: %s\n", parameters[1]);
        }
      }
      else if (url.getPath().contains("/search")) {
        if (parameters[0].equals("s")) {
          String query = parameters[1];
          String foundStrings = "Here's what we found for the search term: "
              + query + "\n\n";

          for (int idx = 0; idx < size; idx++) {
            if (stringPool[idx].contains(query)) {
              foundStrings += stringPool[idx] + '\n';
            }
          }

          return foundStrings;
        }
      }

      return "404 Not Found!";
    }
  }
}

class SearchEngine {
  public static void main(String[] args) throws IOException {
    if (args.length == 0){
      System.out.println("Missing port number! Try any number between 1024 to 49151");
      return;
    }

    int port = Integer.parseInt(args[0]);

    Server.start(port, new Handler());
  }
}
