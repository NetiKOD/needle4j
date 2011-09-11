package de.akquinet.jbosscc.needle.injection.method;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.injection.CurrentUser;
import de.akquinet.jbosscc.needle.injection.InjectionProvider;
import de.akquinet.jbosscc.needle.injection.InjectionTargetInformation;
import de.akquinet.jbosscc.needle.injection.constuctor.User;
import de.akquinet.jbosscc.needle.injection.constuctor.UserDao;
import de.akquinet.jbosscc.needle.junit.NeedleRule;

public class MethodInjectionTest {

	private final User currentUser = new User();

	private final InjectionProvider<User> currentUserprovider = new InjectionProvider<User>() {
		@Override
		public boolean verify(final InjectionTargetInformation information) {
			return information.getAnnotation(CurrentUser.class) != null;
		}

		@Override
		public Object getKey(final InjectionTargetInformation information) {
			return CurrentUser.class;
		}

		@Override
		public User getInjectedObject(final Class<?> type) {
			return currentUser;
		}
	};

	@Rule
	public NeedleRule needleRule = new NeedleRule(currentUserprovider);

	@ObjectUnderTest
	private UserDao userDao;


	@Test
	public void testConstuctorInjection() throws Exception {
		User currentUser2 = userDao.getCurrentUser();

		Assert.assertSame(currentUser, currentUser2);
	}



}
