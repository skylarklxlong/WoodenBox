package net.nightwhistler.htmlspanner.handlers.attributes;

import android.text.SpannableStringBuilder;

import net.nightwhistler.htmlspanner.SpanStack;
import net.nightwhistler.htmlspanner.handlers.StyledTextHandler;
import net.nightwhistler.htmlspanner.spans.BorderSpan;
import net.nightwhistler.htmlspanner.style.Style;

import org.htmlcleaner.TagNode;

import online.himakeit.skylark.util.LogUtils;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/23/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class BorderAttributeHandler extends WrappingStyleHandler {

    public BorderAttributeHandler(StyledTextHandler handler) {
        super(handler);
    }

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end,
                              Style useStyle, SpanStack spanStack) {

        if ( node.getAttributeByName("border") != null ) {
            LogUtils.show("BorderAttributeHandler", "Adding BorderSpan from " + start + " to " + end);
            spanStack.pushSpan(new BorderSpan(useStyle, start, end, getSpanner().isUseColoursFromStyle() ), start, end);
        }

        super.handleTagNode(node, builder, start, end, useStyle, spanStack);

    }


}
