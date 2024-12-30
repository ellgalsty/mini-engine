package com.tonir.demo.presenters.utils.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.tonir.demo.managers.API;
import com.tonir.demo.notification.INotificationContainer;
import com.tonir.demo.notification.INotificationProvider;
import com.tonir.demo.notification.NotificationWidget;
import com.tonir.demo.presenters.UI;
import com.tonir.demo.presenters.utils.ColorLibrary;
import com.tonir.demo.utils.Squircle;
import lombok.Getter;
import lombok.Setter;

public class OffsetButton extends Table implements INotificationContainer {

    @Getter
    protected BorderedTable frontTable;
    protected float offset;
    private ClickListener listener;
    protected Cell<BorderedTable> frontCell;
    protected Table backgroundTable = new Table();

    @Setter
    @Getter
    protected Runnable onClick;
    @Setter
    protected Runnable onTouchDown;
    @Setter
    protected float pressDuration = 0.05f;
    protected boolean pressing;
    protected boolean shouldRelease;
    protected boolean clicked;
    protected boolean releasing;
    protected float timer;

    private final Vector2 size = new Vector2();

    @Getter
    @Setter
    protected boolean enabled = true;
    @Getter
    protected boolean visuallyEnabled = true;
    @Getter
    protected Style style;

    // notifications
    @Getter
    protected NotificationWidget notificationWidget;
    @Setter
    private int notificationAlignment = Align.topLeft;
    @Setter
    private float notificationOffsetX = 30;
    @Setter
    private float notificationOffsetY = 30;

    public OffsetButton () {

    }

    public OffsetButton (Style style) {
        build(style);
    }

    public void build (Style style) {
        build();
        setStyle(style);
    }

    public void build () {
        frontTable = constructFrontTable();
        add(backgroundTable).grow();
        frontCell = backgroundTable.add(frontTable).grow();
        buildInner(frontTable);

        initListeners();
    }

    protected BorderedTable constructFrontTable () {
        final BorderedTable frontTable = new BorderedTable();
        frontTable.setTouchable(Touchable.disabled);
        return frontTable;
    }

    protected void buildInner (Table container) {

    }

    protected void initListeners () {
        listener = new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (API.get(UI.class).isButtonPressed()) return false;
                if (isAnimating()) return false;

                // get the size before animations
                size.set(getWidth(), getHeight());

                pressing = true;
                API.get(UI.class).setButtonPressed(true);
                if (onTouchDown != null) onTouchDown.run();
                // TODO: 30.12.24 play sound
//                API.get(AudioController.class).postGlobalEvent(WwiseCatalogue.EVENTS.BUTTON_TOUCH_DOWN);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (pressing) {
                    // schedule release
                    shouldRelease = true;
                } else releasing = true;

                API.get(UI.class).setButtonPressed(false);
                // TODO: 30.12.24 play sound
//                API.get(AudioController.class).postGlobalEvent(WwiseCatalogue.EVENTS.BUTTON_TOUCH_UP);
            }

            @Override
            public void clicked (InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                clicked = true;
            }
        };
        addListener(listener);
        setTouchable(Touchable.enabled);
    }

    protected boolean isAnimating () {
        return pressing || releasing;
    }

    protected boolean actLogicSkip = false;

    @Override
    public void act (float delta) {
        super.act(delta);

        if (actLogicSkip) return;

        if (!pressing && shouldRelease) {
            releasing = true;
            shouldRelease = false;
        }

        if (!isAnimating()) return;

        timer += delta;

        if (pressing) {
            final float padBottom = MathUtils.clamp(Interpolation.sineIn.apply(offset, 0, timer / pressDuration), 0, offset);
            final float padTop = offset - padBottom;
            padTop(padTop);
            frontCell.padBottom(padBottom);

            if (timer >= pressDuration) {
                frontCell.padBottom(0);
                pressing = false;
                timer = 0;
            }
        } else if (releasing) {
            final float padBottom = MathUtils.clamp(Interpolation.sineOut.apply(0, offset, timer / pressDuration), 0, offset);
            final float padTop = offset - padBottom;
            padTop(padTop);
            frontCell.padBottom(padBottom);

            if (timer >= pressDuration) {
                frontCell.padBottom(offset);
                releasing = false;
                timer = 0;

                if (clicked) {
                    clicked = false;
                    triggerClicked();
                }
            }
        }

        invalidate();
    }

    protected void triggerClicked () {
        if (onClick == null) return;
        onClick.run();
    }

    public boolean isDisabled () {
        return !enabled;
    }

    public void enable () {
        this.enabled = true;
        setTouchable(Touchable.enabled);
        visuallyEnable();
    }

    public void disable () {
        this.enabled = false;
        setTouchable(Touchable.disabled);
        visuallyDisable();
    }

    public void visuallyEnable () {
        this.visuallyEnabled = true;
        // update background
        frontTable.setBackground(style.getInnerBackground(true));
        setBackground(style.getOuterBackground(true));
    }

    public void visuallyDisable () {
        this.visuallyEnabled = false;
        // update background
        frontTable.setBackground(style.getInnerBackground(false));
        setBackground(style.getOuterBackground(false));
    }

    protected void updateVisually () {
        if (enabled) {
            visuallyEnable();
        } else {
            visuallyDisable();
        }
    }

    public void setStyle (Style style) {
        this.style = style;

        final Squircle background = style.background;
        if (background == null) {
            this.frontTable.setBackground((Drawable) null);

            setBackground((Drawable) null);
        } else {
            setBackground(background.getDrawable(style.enabledBorderColor));
            this.frontTable.setBackground(background.getDrawable(style.enabledBackgroundColor));
        }

        final Squircle border = style.border;
        if (border == null) {
            this.frontTable.setBorderDrawable(null);
        } else {
            this.frontTable.setBorderDrawable(border.getDrawable(style.enabledBorderColor));
        }

        this.offset = style.offset;
        this.frontCell.padBottom(offset);
    }

    public void setPad (float pad) {
        this.frontCell.padBottom(offset);
        this.updateVisually();
    }

    public void setOffset (float offset) {
        this.offset = offset;
        this.frontCell.padBottom(offset);
        this.updateVisually();
    }

    @Override
    public void addNotificationWidget (NotificationWidget widget) {
        this.notificationWidget = widget;
        addActor(widget);
        updateNotificationWidgetPosition();
    }

    public void addNotificationWidget () {
        addNotificationWidget(INotificationProvider.Priority.RED);
    }

    public void addNotificationWidget (INotificationProvider.Priority priority) {
        final NotificationWidget widget = new NotificationWidget();
        widget.setPriority(priority);
        setNotificationAlignment(Align.topRight);
        addNotificationWidget(widget);
        hideNotification();
    }

    @Override
    protected void sizeChanged () {
        super.sizeChanged();
        if (notificationWidget != null) {
            updateNotificationWidgetPosition();
        }

        if (clickBox != null) {
            updateClickBox();
        }
    }

    private void updateNotificationWidgetPosition () {
        if (notificationWidget == null) return;

        final float posX;
        final float posY;

        if (Align.isTop(notificationAlignment)) {
            posY = getHeight() - notificationWidget.getHeight() / 2.0f - notificationOffsetY;
        } else {
            posY = -notificationWidget.getHeight() / 2.0f + notificationOffsetY;
        }

        if (Align.isLeft(notificationAlignment)) {
            posX = -notificationWidget.getWidth() / 2.0f + notificationOffsetX;
        } else {
            posX = getWidth() - notificationWidget.getWidth() / 2.0f - notificationOffsetX;
        }

        notificationWidget.setPosition(posX, posY);
    }

    @Override
    public boolean isShowNumber () {
        return false;
    }

    @Override
    public boolean isShowNotification () {
        return true;
    }

    public enum Style {
        // new pastel colors
        GREEN_35_30(Squircle.SQUIRCLE_35, Squircle.SQUIRCLE_35_BORDER, ColorLibrary.get("#6b9d55"), ColorLibrary.get("#9bd781"), 30),
        GREEN_35_15(Squircle.SQUIRCLE_35, Squircle.SQUIRCLE_35_BORDER, ColorLibrary.get("#6b9d55"), ColorLibrary.get("#9bd781"), 15),
        BLUE_35_30(Squircle.SQUIRCLE_35, Squircle.SQUIRCLE_35_BORDER, ColorLibrary.get("#2e8bd3"), ColorLibrary.get("#43a6f2"), 30),
        YELLOW_35_30(Squircle.SQUIRCLE_35, Squircle.SQUIRCLE_35_BORDER, ColorLibrary.get("#99825a"), ColorLibrary.get("#ddb46d"), 30),
        YELLOW_35_20(Squircle.SQUIRCLE_35, Squircle.SQUIRCLE_35_BORDER, ColorLibrary.get("#99825a"), ColorLibrary.get("#ddb46d"), 20),
        RED_35_30(Squircle.SQUIRCLE_35, Squircle.SQUIRCLE_35_BORDER, ColorLibrary.get("#b34140"), ColorLibrary.get("#d95454"), 39),
        RED_35_15(Squircle.SQUIRCLE_35, Squircle.SQUIRCLE_35_BORDER, ColorLibrary.get("#b34140"), ColorLibrary.get("#d95454"), 15),
        WHITE_GRAY_35_30(Squircle.SQUIRCLE_35, Squircle.SQUIRCLE_35_BORDER, ColorLibrary.get("#918377"), ColorLibrary.get("#f4e7de"), 30),

        EMPTY(null, null, ColorLibrary.get("#f4e7de"), ColorLibrary.get("#f4e7de"), 0),
        ;

        private final Squircle background;
        private final Squircle border;
        private final Color enabledBorderColor;
        @Getter
        private final Color enabledBackgroundColor;
        @Getter
        private final Color disabledBorderColor;
        @Getter
        private final Color disabledBackgroundColor;
        private final float offset;

        Style (Squircle background, Squircle border, Color enabledBorderColor, Color enabledBackgroundColor, float offset) {
            this(background, border, enabledBorderColor, enabledBackgroundColor, null, null, offset);
        }

        Style (Squircle background, Squircle border, Color enabledBorderColor, Color enabledBackgroundColor, Color disabledBorderColor, Color disabledBackgroundColor, float offset) {
            this.background = background;
            this.border = border;
            this.enabledBorderColor = enabledBorderColor;
            this.enabledBackgroundColor = enabledBackgroundColor;
            this.disabledBorderColor = disabledBorderColor == null ? ColorLibrary.get("#acacac") : disabledBorderColor;
            this.disabledBackgroundColor = disabledBackgroundColor == null ? ColorLibrary.get("#838484") : disabledBackgroundColor;
            this.offset = offset;
        }

        public Drawable getInnerBackground (boolean enabled) {
            if (border == null) return null;
            if (enabled) {
                return border.getDrawable(enabledBorderColor);
            }
            return border.getDrawable(disabledBorderColor);
        }

        public Drawable getOuterBackground (boolean enabled) {
            if (background == null) return null;
            if (enabled) {
                return background.getDrawable(enabledBackgroundColor);
            }
            return background.getDrawable(disabledBackgroundColor);
        }
    }


    // click box
    private Table clickBox;
    private float clickBoxPadX;
    private float clickBoxPadY;
    private float clickBoxOffsetX;
    private float clickBoxOffsetY;

    public void initClickBox () {
        if (clickBox != null) return;
        clickBox = new Table();
        addActor(clickBox);
        clickBox.setTouchable(Touchable.enabled);
    }

    public void setClickBoxPad (float pad) {
        setClickBoxPad(pad, pad);
    }

    public void setClickBoxPad (float padX, float padY) {
        if (clickBox == null) initClickBox();
        this.clickBoxPadX = padX;
        this.clickBoxPadY = padY;

        updateClickBox();
    }

    public void setClickBoxOffset (float offset) {
        setClickBoxOffset(offset, offset);
    }

    public void setClickBoxOffset (float offsetX, float offsetY) {
        if (clickBox == null) initClickBox();
        this.clickBoxOffsetX = offsetX;
        this.clickBoxOffsetY = offsetY;

        updateClickBox();
    }

    protected void updateClickBox () {
        final float width = clickBoxPadX + getWidth();
        final float height = clickBoxPadY + getHeight();
        clickBox.setSize(width, height);

        final float x = -clickBoxPadX / 2.0f + clickBoxOffsetX;
        final float y = -clickBoxPadY / 2.0f + clickBoxOffsetY;
        clickBox.setPosition(x, y);
    }

    @Override
    public void clearChildren () {
        super.clearChildren();
        if (clickBox != null) {
            addActor(clickBox);
        }
    }

    @Override
    public void setBackground (Drawable background) {
        backgroundTable.setBackground(background);
    }

    public void showNotification () {
        if (notificationWidget != null) notificationWidget.setVisible(true);
    }

    public void hideNotification () {
        if (notificationWidget != null) notificationWidget.setVisible(false);
    }
}

