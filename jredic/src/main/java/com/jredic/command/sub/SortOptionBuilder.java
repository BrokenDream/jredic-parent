package com.jredic.command.sub;

import com.jredic.util.Strings;
import java.util.ArrayList;
import java.util.List;

/**
 * The build class for options of Command 'SORT'.
 * <p>
 * see {@link com.jredic.command.KeyCommand#SORT}
 * see <a href="https://redis.io/commands/sort">Redis Sort Command</a>.
 *
 * @author David.W
 */
public class SortOptionBuilder {

    public static final String MODIFIER_ASC = "ASC";
    public static final String MODIFIER_DESC = "DESC";
    public static final String MODIFIER_ALPHA = "ALPHA";
    public static final String MODIFIER_LIMIT = "LIMIT";

    public static final String OPTION_BY = "BY";
    public static final String OPTION_GET = "GET";
    public static final String OPTION_STORE = "STORE";

    private String order;
    private boolean alpha;
    private boolean limit;
    private int offset;
    private int count;
    private String by;
    private List<String> get = new ArrayList<>();

    public SortOptionBuilder asc(){
        this.order = MODIFIER_ASC;
        return this;
    }

    public SortOptionBuilder desc(){
        this.order = MODIFIER_DESC;
        return this;
    }

    public SortOptionBuilder alpha(){
        this.alpha = true;
        return this;
    }

    public SortOptionBuilder limit(int offset, int count){
        this.limit = true;
        this.offset = offset;
        this.count = count;
        return this;
    }

    public SortOptionBuilder by(String pattern){
        this.by = pattern;
        return this;
    }

    public SortOptionBuilder get(String pattern){
        this.get.add(pattern);
        return this;
    }

    public List<String> build(){
        List<String> options = new ArrayList<>();
        //by
        if(!Strings.isNullOrEmpty(by)){
            options.add(OPTION_BY);
            options.add(by);
        }
        //limit
        if(limit){
            options.add(MODIFIER_LIMIT);
            options.add(Integer.toString(offset));
            options.add(Integer.toString(count));
        }
        //gets
        if(get.size() > 0){
            for(String pattern : get){
                options.add(OPTION_GET);
                options.add(pattern);
            }
        }
        //order
        if(!Strings.isNullOrEmpty(order)){
            options.add(order);
        }
        //alpha
        if(alpha){
            options.add(MODIFIER_ALPHA);
        }
        return options;
    }

}
