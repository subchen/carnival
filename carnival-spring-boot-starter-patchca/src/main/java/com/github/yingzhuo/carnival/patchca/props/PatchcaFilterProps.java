/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca.props;

import com.github.yingzhuo.carnival.patchca.FilterType;
import com.github.yingzhuo.carnival.patchca.PatchcaFilter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

@Data
@ConfigurationProperties(prefix = "carnival.patchca.filter")
public class PatchcaFilterProps {

    private String name;
    private int order;
    private String[] urlPatterns;
    private int width = 160;
    private int height = 70;
    private FilterType filterType = FilterType.CURVES;
    private String sessionAttributeName = "PATCHCA_SESSION_ATTRIBUTE_NAME";

    public PatchcaFilterProps() {
        setUrlPatterns(new String[]{"/patchca.png", "/patchca"});
        setName(PatchcaFilter.class.getSimpleName());
        setOrder(Ordered.LOWEST_PRECEDENCE);
    }
}
