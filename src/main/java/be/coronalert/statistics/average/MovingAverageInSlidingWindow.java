/*
 * Coronalert / coronalert-statistics-server
 *
 * (C) 2020, Ixor CVBA
 *
 * Ixor CVBA and all other contributors /
 * copyright owners license this file to you under the Apache
 * License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package be.coronalert.statistics.average;

import java.util.LinkedList;
import java.util.Queue;

public class MovingAverageInSlidingWindow {

  private int windowsize;
  private Queue<Integer> queue;
  private int sum;

  /**
   * Creates a MovingAverageInSlidingWindow instance.
   * @param windowsize The windowsize used to calculate the avarage.
   */
  public MovingAverageInSlidingWindow(int windowsize) {
    this.windowsize = windowsize;
    this.queue = new LinkedList();
    this.sum = 0;
  }

  /**
   * Calculates the moving average after inserting the element in the queue.
   */
  public int findMovingAverage(int n) {
    if (queue.size() > windowsize - 1) {
      sum = sum - queue.poll();
    }
    queue.offer(n);
    sum = sum + n;
    return  sum / queue.size();
  }

}
