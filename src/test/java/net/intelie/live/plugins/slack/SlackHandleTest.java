/*
 * (C) Copyright 2016 Intelie (http://intelie.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.intelie.live.plugins.slack;

import net.intelie.live.PrefixedLive;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class SlackHandleTest {
    @Test
    public void whenClosing() throws Exception {
        PrefixedLive live = mock(PrefixedLive.class);
        SlackHandler handle = new SlackHandler(live);
        handle.close();

        verify(live).undoAll();
    }
}
