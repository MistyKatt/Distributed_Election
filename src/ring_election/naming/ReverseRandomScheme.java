/*
 * Decompiled with CFR 0_118.
 */
package davis.naming;

import davis.naming.IdScheme;
import davis.naming.RandomScheme;
import java.util.Collections;
import java.util.List;

public class ReverseRandomScheme
implements IdScheme {
    @Override
    public List<Integer> get(int n) {
        List<Integer> list = new RandomScheme().get(n);
        list = list.subList(0, n);
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }
}

