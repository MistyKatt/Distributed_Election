/*
 * Decompiled with CFR 0_118.
 */
package davis.naming;

import davis.naming.IdScheme;
import java.util.ArrayList;
import java.util.List;

public class FirstDistinguishedScheme
implements IdScheme {
    @Override
    public List<Integer> get(int n) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(1);
        for (int i = 1; i < n; ++i) {
            arrayList.add(0);
        }
        return arrayList;
    }
}

