package org.example;

import com.webforj.App;
import com.webforj.annotation.AppTitle;
import com.webforj.annotation.Routify;
import com.webforj.annotation.StyleSheet;

/**
 * A simple HelloWorld app.
 */
@Routify(packages = "org.example.views")
@AppTitle("Demo Step 3")
@StyleSheet("ws://css/app.css")
public class Application extends App{
}
