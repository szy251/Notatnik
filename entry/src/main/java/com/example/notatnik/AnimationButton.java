package com.example.notatnik;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Component;

public class AnimationButton extends AnimatorProperty {

    public AnimationButton(float start, float end, int duration, Component component, boolean hide) {

        super(component);
        this.alpha(end);
        this.alphaFrom(start);
        this.setDuration(duration);
        if (hide)
        this.setStateChangedListener(new StateChangedListener() {
            @Override
            public void onStart(Animator animator) {

            }

            @Override
            public void onStop(Animator animator) {

            }

            @Override
            public void onCancel(Animator animator) {

            }

            @Override
            public void onEnd(Animator animator) {
                component.setVisibility(Component.HIDE);
            }

            @Override
            public void onPause(Animator animator) {

            }

            @Override
            public void onResume(Animator animator) {

            }
        });
        else
            this.setStateChangedListener(new StateChangedListener() {
                @Override
                public void onStart(Animator animator) {
                    component.setVisibility(Component.VISIBLE);
                }

                @Override
                public void onStop(Animator animator) {

                }

                @Override
                public void onCancel(Animator animator) {

                }

                @Override
                public void onEnd(Animator animator) {
                }

                @Override
                public void onPause(Animator animator) {

                }

                @Override
                public void onResume(Animator animator) {

                }
            });

    }

    @Override
    public void start() {
        super.start();
    }
}
