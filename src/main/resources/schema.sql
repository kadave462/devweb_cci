CREATE TABLE IF NOT EXISTS ranking (
    team_id UUID PRIMARY KEY,
    team_name VARCHAR(255) NOT NULL,
    rank INTEGER NOT NULL,
    match_played_count INTEGER NOT NULL,
    match_won_count INTEGER NOT NULL,
    match_lost_count INTEGER NOT NULL,
    draw_count INTEGER NOT NULL,
    goal_for_count INTEGER NOT NULL,
    goal_against_count INTEGER NOT NULL,
    goal_difference INTEGER NOT NULL,
    points INTEGER NOT NULL
);
