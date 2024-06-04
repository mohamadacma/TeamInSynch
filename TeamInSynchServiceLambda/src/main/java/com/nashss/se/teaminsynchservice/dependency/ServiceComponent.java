package com.nashss.se.teaminsynchservice.dependency;

import com.nashss.se.teaminsynchservice.activity.AddMemberActivity;
import com.nashss.se.teaminsynchservice.activity.DeleteMemberActivity;
import com.nashss.se.teaminsynchservice.activity.SearchMembersActivity;
import com.nashss.se.teaminsynchservice.activity.UpdateMemberActivity;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Team In Synch Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     *
     * @return AddMemberActivity
     */
    AddMemberActivity provideAddMemberActivity();

    /**
     * Provides the relevant activity.
     *
     * @return UpdateMemberActivity
     */
    UpdateMemberActivity provideUpdateMemberActivity();

    /**
     * Provides the relevant activity.
     *
     * @return DeleteMemberActivity
     */
    DeleteMemberActivity provideDeleteMemberActivity();
    /**
     * Provides the relevant activity.
     *
     * @return SearchMembersActivity
     */
    SearchMembersActivity provideSearchMembersActivity();
}

