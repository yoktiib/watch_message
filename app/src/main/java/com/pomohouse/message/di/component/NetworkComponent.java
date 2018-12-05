package com.pomohouse.message.di.component;

import com.pomohouse.message.ContactActivity;
import com.pomohouse.message.MainActivity;
import com.pomohouse.message.controller.SendMessageController;
import com.pomohouse.message.di.module.AppModule;
import com.pomohouse.message.di.module.NetworkModule;
import com.pomohouse.message.view.AllContactFragment;
import com.pomohouse.message.view.MessageFragment;
import com.pomohouse.message.view.StickerListFragment;
import com.pomohouse.message.view.StickerPreviewFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {

    void injectToMainActivity(MainActivity activity);

    void injectToContactActivity(ContactActivity activity);

    void inject(AllContactFragment fragment);

    void inject(MessageFragment fragment);

    void inject(SendMessageController senMessage);

    void inject(StickerListFragment stickerFragment);

    void inject(StickerPreviewFragment stickerPreviewFragment);
}
