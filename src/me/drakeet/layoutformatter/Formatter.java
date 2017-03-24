package me.drakeet.layoutformatter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by drakeet(http://drakeet.me)
 * Date: 16/4/10 15:01
 */
class Formatter {

    private static int priority = 0;
    private static final int MAX_PRIORITY = Integer.MAX_VALUE / 2;
    private static int maxPriority = MAX_PRIORITY;
    private static HashMap<String, Integer> sRule = new HashMap<String, Integer>();


    private static int getPriority() {
        return priority;
    }


    private static int getNextPriority() {
        return ++priority;
    }


    private static int getMaxNextPriority() {
        return ++maxPriority;
    }


    static {
        sRule.put("xmlns:android", getNextPriority());
        sRule.put("xmlns:app", getNextPriority());
        sRule.put("xmlns:tools", getNextPriority());
        sRule.put("style", getNextPriority());
        sRule.put("theme", getNextPriority());
        sRule.put(android("id"), getNextPriority());
        sRule.put(android("tag"), getNextPriority());

        sRule.put(android("layout_above"), getNextPriority());
        sRule.put(android("layout_below"), getNextPriority());
        sRule.put(android("layout_toLeftOf"), getNextPriority());
        sRule.put(android("layout_toStartOf"), getNextPriority());
        sRule.put(android("layout_toRightOf"), getNextPriority());
        sRule.put(android("layout_toEndOf"), getNextPriority());
        sRule.put(android("layout_alignStart"), getNextPriority());
        sRule.put(android("layout_alignRight"), getNextPriority());
        sRule.put(android("layout_alignTop"), getNextPriority());
        sRule.put(android("layout_alignBottom"), getNextPriority());

        sRule.put(android("visibility"), getNextPriority());
        sRule.put(tools("visibility"), getNextPriority());
        sRule.put(android("layout_width"), getNextPriority());
        sRule.put(android("layout_height"), getNextPriority());
        sRule.put(android("layout_weight"), getNextPriority());
        sRule.put(android("layout_gravity"), getNextPriority());

        sRule.put(android("layout_alignParentStart"), getNextPriority());
        sRule.put(android("layout_alignParentRight"), getNextPriority());
        sRule.put(android("layout_alignParentTop"), getNextPriority());
        sRule.put(android("layout_alignParentBottom"), getNextPriority());
        sRule.put(android("layout_centerInParent"), getNextPriority());
        sRule.put(android("layout_centerVertical"), getNextPriority());
        sRule.put(android("layout_centerHorizontal"), getNextPriority());

        sRule.put(android("layout_margin"), getNextPriority());
        sRule.put(android("layout_marginLeft"), getNextPriority());
        sRule.put(android("layout_marginStart"), getPriority());
        sRule.put(android("layout_marginRight"), getNextPriority());
        sRule.put(android("layout_marginEnd"), getPriority());
        sRule.put(android("layout_marginTop"), getNextPriority());
        sRule.put(android("layout_marginBottom"), getNextPriority());

        sRule.put(android("padding"), getNextPriority());
        sRule.put(android("paddingLeft"), getNextPriority());
        sRule.put(android("paddingStart"), getPriority());
        sRule.put(android("paddingRight"), getNextPriority());
        sRule.put(android("paddingEnd"), getPriority());
        sRule.put(android("paddingTop"), getNextPriority());
        sRule.put(android("paddingBottom"), getNextPriority());

        sRule.put(android("layoutAnimation"), getNextPriority());
        sRule.put(android("orientation"), getNextPriority());
        sRule.put(android("gravity"), getNextPriority());
        sRule.put(android("minHeight"), getNextPriority());
        sRule.put(android("maxHeight"), getNextPriority());
        sRule.put(android("minWidth"), getNextPriority());
        sRule.put(android("maxWidth"), getNextPriority());
        sRule.put(android("weightSum"), getNextPriority());
        sRule.put(android("background"), getNextPriority());
        sRule.put(android("foreground"), getNextPriority());
        sRule.put(android("clickable"), getNextPriority());
        sRule.put(android("onClick"), getNextPriority());
        sRule.put(android("scaleType"), getNextPriority());
        sRule.put(android("inputType"), getNextPriority());
        sRule.put(android("autoLink"), getNextPriority());
        sRule.put(android("translationZ"), getNextPriority());
        sRule.put(android("elevation"), getNextPriority());

        sRule.put(android("alpha"), getMaxNextPriority());
        sRule.put(android("textColorHint"), getMaxNextPriority());
        sRule.put(android("textColor"), getMaxNextPriority());
        sRule.put(android("hint"), getMaxNextPriority());
        sRule.put(tools("hint"), getMaxNextPriority());
        sRule.put(android("text"), getMaxNextPriority());
        sRule.put(tools("text"), getMaxNextPriority());
        sRule.put(android("src"), getMaxNextPriority());
        sRule.put(tools("src"), getMaxNextPriority());
    }


    private static int getLinePriority(String line) {
        try {
            return sRule.get(line.trim().split("=")[0]);
        } catch (NullPointerException e) {
            return MAX_PRIORITY;
        }
    }


    static String retrofit(String xml) {
        // @formatter:off
        return xml.replace("\" >"               , "\"" + lineSeparator() + ">")
                  .replace("\">"                , "\"" + lineSeparator() + ">")
                  .replace("\" />"              , "\"" + lineSeparator() + "/>")
                  .replace("\"/>"               , "\"" + lineSeparator() + "/>")
                  .replace("/><"                , "/>" + lineSeparator() + "<")
                  .replace("\"fill_parent\""    , "\"match_parent\"")
                  .replace("dip\""              , "dp\"");
    }


    static String repair(String xml) {
        return xml.replace("\"" + lineSeparator() + ">" , "\">" )
                  .replace("\"" + lineSeparator() + "/>", "\"/>" );
        // @formatter:on
    }


    // TODO: 16/4/11 Need to support the multi-lines strings
    static String apply(String xml) {
        xml = retrofit(xml);
        String[] _lines = xml.split(lineSeparator());
        List<String> attrs = Arrays.asList(_lines);
        int start = 0, end;
        boolean skip = false;
        for (int i = 0; i < attrs.size(); i++) {
            String attr = attrs.get(i).trim();
            if (!attr.startsWith("</") && attr.startsWith("<")) {
                start = i;
                skip = false;
            } else if (attr.startsWith("</")) {
                skip = true;
            } else if (attr.endsWith(">")) {
                if (!skip) {
                    end = i;
                    resort(attrs, start + 1, end);
                } else {
                    skip = false;
                }
            }
        }
        String result = "";
        for (String line : attrs) {
            result += line + lineSeparator();
        }

        return repair(result);
    }


    private static void resort(List<String> lines, int start, int end) {
        if (start >= lines.size() || start > end || end >= lines.size()) {
            return;
        }
        int i, j;
        String temp;
        for (i = start; i < end - 1; i++) {
            for (j = start; j < end - 1 - i + start; j++) {
                if (getLinePriority(lines.get(j)) > getLinePriority(lines.get(j + 1))) {
                    temp = lines.get(j);
                    lines.set(j, lines.get(j + 1));
                    lines.set(j + 1, temp);
                }
            }
        }
    }


    private static String android(String name) {
        return String.format("android:%s", name);
    }


    private static String app(String name) {
        return String.format("app:%s", name);
    }


    private static String tools(String name) {
        return String.format("tools:%s", name);
    }


    private static String lineSeparator() {
        return "\n";
    }
}
