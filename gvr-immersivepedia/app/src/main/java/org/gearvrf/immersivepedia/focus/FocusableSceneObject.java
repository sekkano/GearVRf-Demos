/* Copyright 2015 Samsung Electronics Co., LTD
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

package org.gearvrf.immersivepedia.focus;

import org.gearvrf.GVRContext;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRTexture;
import org.gearvrf.immersivepedia.GazeController;
import org.gearvrf.immersivepedia.input.TouchPadInput;
import org.gearvrf.io.GVRTouchPadGestureListener.Action;
import org.gearvrf.utility.Log;

public class FocusableSceneObject extends GVRSceneObject {

    private boolean focus = false;
    public FocusListener focusListener = null;
    public String tag = null;
    public boolean showInteractiveCursor = true;
    private OnClickListener onClickListener;
    private OnGestureListener onGestureListener;
    public float[] hitLocation;

    public FocusableSceneObject(GVRContext gvrContext) {
        super(gvrContext);
    }

    public FocusableSceneObject(GVRContext gvrContext, GVRMesh gvrMesh, GVRTexture gvrTexture) {
        super(gvrContext, gvrMesh, gvrTexture);
    }

    public FocusableSceneObject(GVRContext gvrContext, float width, float height, GVRTexture t) {
        super(gvrContext, width, height, t);
    }

    public void dispatchGainedFocus() {
        if (this.focusListener != null) {
            this.focusListener.gainedFocus(this);
        }
        if (showInteractiveCursor) {
            GazeController.get().enableInteractiveCursor();
        }
    }

    public void dispatchLostFocus() {
        if (this.focusListener != null) {
            focusListener.lostFocus(this);
        }
        if (showInteractiveCursor) {
            GazeController.get().disableInteractiveCursor();
        }
    }

    public void setFocus(boolean state) {
        if (state == true && focus == false) {
            focus = true;
            this.dispatchGainedFocus();
            return;
        }

        if (state == false && focus == true) {
            focus = false;
            this.dispatchLostFocus();
            return;
        }
    }

    public void dispatchInFocus() {
        if (this.focusListener != null) {
            this.focusListener.inFocus(this);
        }
        if (showInteractiveCursor) {
            GazeController.get().enableInteractiveCursor();
        }
    }

    public void dispatchInClick() {
        if (this.onClickListener != null)
            this.onClickListener.onClick();
    }

    public boolean hasFocus() {
        return focus;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnGestureListener(OnGestureListener onGestureListener) {
        this.onGestureListener = onGestureListener;
    }

    public boolean isFocus() {
        return focus;
    }

    public void dispatchInGesture(Action swipeDirection) {
        if (this.onGestureListener != null) {

            if (TouchPadInput.getCurrent().swipeDirection == Action.None)
                onGestureListener.onSwipeIgnore();
            else if (TouchPadInput.getCurrent().swipeDirection == Action.SwipeForward)
                onGestureListener.onSwipeForward();
            else if (TouchPadInput.getCurrent().swipeDirection == Action.SwipeBackward)
                onGestureListener.onSwipeBack();
            else if (TouchPadInput.getCurrent().swipeDirection == Action.SwipeUp)
                onGestureListener.onSwipeUp();
            else if (TouchPadInput.getCurrent().swipeDirection == Action.SwipeDown)
                onGestureListener.onSwipeDown();
        }
    }
}
