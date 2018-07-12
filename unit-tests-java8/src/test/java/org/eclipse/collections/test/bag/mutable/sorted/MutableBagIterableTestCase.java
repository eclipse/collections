/*
 * Copyright (c) 2024 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.bag.mutable.sorted;

import org.eclipse.collections.api.bag.MutableBagIterable;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.test.collection.mutable.MutableCollectionTestCase;
import org.junit.Assert;
import org.junit.Test;

import static org.eclipse.collections.test.IterableTestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public interface MutableBagIterableTestCase extends MutableCollectionTestCase
{
    @Override
    <T> MutableBagIterable<T> newWith(T... elements);

    @Test
    default void MutableBagIterable_addOccurrences()
    {
        MutableBagIterable<Integer> mutableBag = this.newWith(1, 2, 2, 3, 3, 3);
        assertEquals(4, mutableBag.addOccurrences(4, 4));
        assertEquals(Bags.immutable.with(1, 2, 2, 3, 3, 3, 4, 4, 4, 4), mutableBag);
        assertEquals(3, mutableBag.addOccurrences(1, 2));
        assertEquals(Bags.immutable.with(1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4), mutableBag);

        assertThrows(
                IllegalArgumentException.class,
                () -> mutableBag.addOccurrences(4, -1));
        assertEquals(Bags.immutable.with(1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4), mutableBag);
        assertEquals(Bags.immutable.with(1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4), mutableBag);

        assertThrows(
                ArithmeticException.class,
                () -> mutableBag.addOccurrences(3, Integer.MAX_VALUE - 1));
        assertEquals(Bags.immutable.with(1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4), mutableBag);

        mutableBag.addOccurrences(5, Integer.MAX_VALUE - 10);
        mutableBag.addOccurrences(6, Integer.MAX_VALUE - 10);
        Assert.assertEquals(-10, mutableBag.size());
    }

    @Test
    default void MutableBagIterable_removeOccurrences()
    {
        MutableBagIterable<Integer> mutableBag = this.newWith(1, 2, 2, 3, 3, 3);
        assertFalse(mutableBag.removeOccurrences(4, 4));
        assertEquals(Bags.immutable.with(1, 2, 2, 3, 3, 3), mutableBag);
        assertTrue(mutableBag.removeOccurrences(1, 2));
        assertEquals(Bags.immutable.with(2, 2, 3, 3, 3), mutableBag);
        assertTrue(mutableBag.removeOccurrences(3, 2));
        assertEquals(Bags.immutable.with(2, 2, 3), mutableBag);
        assertTrue(mutableBag.removeOccurrences(2, 2));
        assertEquals(Bags.immutable.with(3), mutableBag);

        assertThrows(
                IllegalArgumentException.class,
                () -> mutableBag.removeOccurrences(4, -1));
    }
}
