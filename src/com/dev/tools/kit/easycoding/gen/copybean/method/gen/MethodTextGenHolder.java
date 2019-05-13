package com.dev.tools.kit.easycoding.gen.copybean.method.gen;

import com.dev.tools.kit.easycoding.gen.copybean.method.gen.impl.CollectionEnum;
import com.dev.tools.kit.easycoding.gen.copybean.method.gen.impl.ObjectMethodTextGen;
import com.intellij.psi.PsiMethod;
import com.dev.tools.kit.easycoding.gen.copybean.method.gen.impl.CollectionMethodTextGen;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:Holder
 * @Author: zhangjianfeng
 * @Date: 2019-05-10
 */
public class MethodTextGenHolder {

    private List<MethodTextGen> methodTextGens;

    public MethodTextGenHolder() {
        initMethodTextGens();
    }

    private void initMethodTextGens() {
        methodTextGens = new ArrayList<>();
        methodTextGens.add(new CollectionMethodTextGen(CollectionEnum.LIST));
        methodTextGens.add(new CollectionMethodTextGen(CollectionEnum.SET));
        methodTextGens.add(new ObjectMethodTextGen());
    }

    public  MethodTextGen getMethodTextGen(PsiMethod psiMethod) {
        for(MethodTextGen methodTextGen : methodTextGens)
        {
            if (methodTextGen.match(psiMethod))
            {
                return methodTextGen;
            }
        }
        return null;
    }
}
