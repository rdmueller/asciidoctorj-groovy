# asciidoctorj-groovy
example asciidoctorj plugin which lets you execute groovy code inline in your asciidoc documents

In order to make use of the liveReload functionality, you have to use chrome and the [LiveReload plugin](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related?hl=en).

Start `gradlew -t asciidoctor` in one shell and `gradlew liveReload` in another. Now you can navigate to `[http://localhost:35729/](http://localhost:35729/)`, turn liveReload in your browser on (icon in the upper right corner) and edit the asciidoc source. Gradle will re-render the html output whenever you save and liveReload will take care of reloading the result in your browser.

**WARNING**: this plugin lets you execute all kind of code in an asciidoc document. Well I think you are a developer and know what you do, but keep in mind that people might not expect this from a document and you might run into security issues.
