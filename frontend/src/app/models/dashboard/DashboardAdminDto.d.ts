type DashboardAdminDto = {
  ticketHealth: TicketHealthStats;
  userHealth: UserHealthStats;

  activities: List<UserActivity>;
};
