CREATE TABLE IF NOT EXISTS teams (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS matches (
    id UUID PRIMARY KEY,
    home_team_id UUID NOT NULL,
    away_team_id UUID NOT NULL,
    home_team_goals INTEGER NOT NULL,
    away_team_goals INTEGER NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    FOREIGN KEY (home_team_id) REFERENCES teams(id),
    FOREIGN KEY (away_team_id) REFERENCES teams(id),
    CHECK (home_team_goals >= 0 AND away_team_goals >= 0),
    CHECK (home_team_id != away_team_id),
    UNIQUE (home_team_id, away_team_id)
);

CREATE TABLE IF NOT EXISTS ranking (
    team_id UUID PRIMARY KEY,
    rank INTEGER NOT NULL,
    match_played_count INTEGER NOT NULL,
    match_won_count INTEGER NOT NULL,
    match_lost_count INTEGER NOT NULL,
    draw_count INTEGER NOT NULL,
    goal_for_count INTEGER NOT NULL,
    goal_against_count INTEGER NOT NULL,
    goal_difference INTEGER NOT NULL,
    points INTEGER NOT NULL,
    FOREIGN KEY (team_id) REFERENCES teams(id)
);