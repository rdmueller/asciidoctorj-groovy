package org.asciidoctor.extension

import org.asciidoctor.extension.BlockProcessor
import org.asciidoctor.ast.AbstractBlock
import org.asciidoctor.extension.Reader

class GroovyBlock extends BlockProcessor {

    private static binding = new Binding()

    GroovyBlock(String name, Map<String, Object> config) {
        super(name, [contexts: [':paragraph'], content_model: ':simple'])
        binding.context = [:]
    }

    def process(AbstractBlock parent, Reader reader, Map<String, Object> attributes) {
        def code = reader.lines().join("\n")
        def shell = new GroovyShell(binding)
        def result = shell.evaluate(code)
        def block="""
<div class="listingblock">
    <div class="content">
        <pre class="CodeRay highlight"><code class="language-groovy" data-lang="groovy">$code</code></pre>
    </div>
    <div class="content" style="margin-top: 3px;">
        <pre class="highlight"><code data-lang="output">$result</code></pre>
    </div>
</div>
"""
        //second parameter is "context" which seems to take one of
        //these values: https://github.com/asciidoctor/asciidoctor/blob/master/lib/asciidoctor/block.rb#L14
        createBlock(parent, 'pass', block, attributes, [:])
    }


}