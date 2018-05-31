package org.asciidoctor.extension

import org.asciidoctor.ast.Block
import org.asciidoctor.ast.Document
import org.asciidoctor.extension.BlockProcessor
import org.asciidoctor.ast.AbstractBlock
import org.asciidoctor.extension.Reader

class GroovyBlock extends Preprocessor {

    private static binding = new Binding()

    static final TAG = 'groovy'

    GroovyBlock( Map<String, Object> config) {
        super(config)
        binding.context = [:]
    }

    void executeAndAppend(List<String> code, List<String> lines){
        def shell = new GroovyShell(binding)
        def result = shell.evaluate(code.join('\n'))

        lines << '.source'
        lines << '[source,groovy]'
        code.each{ line ->
            lines << line
        }
        lines << ' '
        lines << '.result'
        lines << '[source,console]'
        "$result".split('\n').each{
            lines << it
        }
        lines << ' '
    }

    PreprocessorReader process(Document document, PreprocessorReader reader) {
        List<String> lines = reader.readLines();

        List<String> newLines = new ArrayList<>();
        for(int i=0; i<lines.size(); i++){
            String line = lines[i];
            if( line.startsWith("[$TAG") && line.endsWith(']')){
                List<String> code = []
                for( ++i; i<lines.size(); i++){
                    if( lines[i].trim().length() ==0)
                        break
                    code << lines[i]
                }
                newLines << line
                newLines << ' '
                executeAndAppend( code, newLines)
                continue
            }
            if (line.trim().length() == 0) {
                newLines << ' '
                continue
            }
            newLines << line
        }

        def attributes = [:]
        newLines.reverseEach {line->
            reader.push_include( line, null, null, 1, attributes);
        }


        reader
    }


}
